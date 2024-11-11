package controller.personaldevelopment;

import controller.accescontrol.BaseRBACController;
import dal.EmployeeDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import dal.ScheduleCampaignDBContext;
import dal.WorkerScheduleDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employee;
import model.Plan;
import model.Product;
import model.ScheduleCampaign;
import model.WorkerSchedule;
import model.accesscontrol.User;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class PersonalDevelopmentCreateController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggedUser) throws ServletException, IOException {
        Date date = Date.valueOf(req.getParameter("date"));
        req.setAttribute("date", date);

        EmployeeDBContext employeeDB = new EmployeeDBContext();
        PlanDBContext planDB = new PlanDBContext();
        int departmentId = employeeDB.getDepartmentIdByUsername(loggedUser.getUsername());
        ArrayList<Plan> plans = planDB.getPlansWithinDate(date, departmentId);
        req.setAttribute("plans", plans);

        if (!plans.isEmpty()) {
            String planIdParam = req.getParameter("planId");
            int planId = planIdParam != null ? Integer.parseInt(planIdParam) : plans.get(0).getPlid();

            ProductDBContext productDB = new ProductDBContext();
            ArrayList<Product> products = productDB.getProductsForPlan(planId);
            req.setAttribute("products", products);

            ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
            ArrayList<ScheduleCampaign> shifts = scheduleDB.getDistinctShiftsForPlanAndDate(planId, date);
            req.setAttribute("shifts", shifts);
        } else {
            req.setAttribute("products", new ArrayList<>()); // Đảm bảo thuộc tính 'products' luôn được thiết lập
            req.setAttribute("shifts", new ArrayList<>()); // Đảm bảo thuộc tính 'shifts' luôn được thiết lập
        }

        req.getRequestDispatcher("/view/personaldevelopment/create.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggedUser) throws ServletException, IOException {
        String dateParam = req.getParameter("date");
        String shift = req.getParameter("shift");
        String planIdParam = req.getParameter("planId");

        if (dateParam == null || shift == null || planIdParam == null || dateParam.isEmpty() || shift.isEmpty() || planIdParam.isEmpty()) {
            req.setAttribute("errorMessage", "Date, shift, and plan are required.");
            doAuthorizedGet(req, resp, loggedUser);
            return;
        }

        Date date = Date.valueOf(dateParam);
        int planId = Integer.parseInt(planIdParam);

        WorkerScheduleDBContext wsDB = new WorkerScheduleDBContext();
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        EmployeeDBContext employeeDB = new EmployeeDBContext();

        int departmentId = employeeDB.getDepartmentIdByUsername(loggedUser.getUsername());
        ArrayList<Employee> employees = employeeDB.getEmployeesByDepartmentId(departmentId);

        boolean hasInsertedAny = false;

        for (String paramName : req.getParameterMap().keySet()) {
            if (paramName.startsWith("quantity_")) {
                String productIdStr = paramName.split("_")[1];
                String quantityStr = req.getParameter(paramName);

                if (quantityStr != null && !quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    int productId = Integer.parseInt(productIdStr);

                    int scid = scheduleDB.getScheduleCampaignId(planId, shift, productId, date);

                    if (scid != -1) {
                        for (Employee employee : employees) {
                            WorkerSchedule ws = new WorkerSchedule();
                            ScheduleCampaign sc = new ScheduleCampaign();
                            sc.setScid(scid);
                            ws.setScheduleCampaign(sc);

                            ws.setEmployee(employee);
                            ws.setQuantity(quantity);

                            wsDB.insert(ws);
                            hasInsertedAny = true;
                        }
                    }
                }
            }
        }

        if (!hasInsertedAny) {
            req.setAttribute("errorMessage", "No records were added. Please enter quantities and try again.");
            doAuthorizedGet(req, resp, loggedUser);
        } else {
            resp.sendRedirect(req.getContextPath() + "/personaldevelopment/detail?date=" + date.toString());
        }
    }
}
