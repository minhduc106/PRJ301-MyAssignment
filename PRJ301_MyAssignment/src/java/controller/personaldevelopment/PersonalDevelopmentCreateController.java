package controller.personaldevelopment;

import controller.accescontrol.BaseRBACController;
import dal.PlanDBContext;
import dal.ProductDBContext;
import dal.ScheduleCampaignDBContext;
import dal.EmployeeDBContext;
import dal.WorkerScheduleDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Plan;
import model.Product;
import model.ScheduleCampaign;
import model.Employee;
import model.WorkerSchedule;
import model.accesscontrol.User;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import model.PlanCampaign;

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
            req.setAttribute("products", new ArrayList<>()); // Ensure 'products' attribute is always set
        }

        ArrayList<Employee> employees = employeeDB.listByLoggedUser(loggedUser);
        req.setAttribute("employees", employees);

        req.getRequestDispatcher("/view/personaldevelopment/create.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggedUser) throws ServletException, IOException {
        String dateParam = req.getParameter("date");
        if (dateParam == null || dateParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Date parameter is missing or invalid.");
            return;
        }

        Date date;
        try {
            date = Date.valueOf(dateParam);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
            return;
        }

        int planId = Integer.parseInt(req.getParameter("planId"));
        String shift = req.getParameter("shift");
        String eid = req.getParameter("employeeId");

        Enumeration<String> parameterNames = req.getParameterNames();
        WorkerScheduleDBContext wsDB = new WorkerScheduleDBContext();
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("quantity_")) {
                String productIdStr = paramName.split("_")[1];
                String quantityStr = req.getParameter(paramName);
                if (quantityStr != null && !quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0) {
                        int productId = Integer.parseInt(productIdStr);
                        int scid = scheduleDB.getScheduleCampaignId(planId, shift, productId, date);

                        if (scid != -1) {
                            WorkerSchedule ws = new WorkerSchedule();
                            ScheduleCampaign sc = new ScheduleCampaign();
                            sc.setScid(scid);
                            ws.setScheduleCampaign(sc);

                            Employee employee = new Employee();
                            employee.setEid(eid);
                            ws.setEmployee(employee);
                            ws.setQuantity(quantity);

                            wsDB.insert(ws);
                        }
                    }
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/personaldevelopment/detail?date=" + date.toString());
    }

}
