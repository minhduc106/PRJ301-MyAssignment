package controller.productionplan;

import dal.PlanDBContext;
import dal.ProductDBContext;
import dal.ScheduleCampaignDBContext;
import dal.PlanCampaignDBContext;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Plan;
import model.Product;
import model.ScheduleCampaign;
import model.PlanCampaign;
import java.text.SimpleDateFormat;

public class ScheduleCampaignCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int plid = Integer.parseInt(request.getParameter("plid"));
        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.getPlanById(plid);

        // Kiểm tra nếu plan là null
        if (plan == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Plan not found with ID: " + plid);
            return;
        }

        // Lấy danh sách các sản phẩm
        ProductDBContext pDB = new ProductDBContext();
        ArrayList<Product> products = pDB.list();

        // Chuẩn bị các ngày từ startd đến endd
        Date startDate = plan.getStartd();
        Date endDate = plan.getEndd();
        

// Cập nhật format date trước khi truyền vào request
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> formattedDates = new ArrayList<>();

        for (long date = startDate.getTime(); date <= endDate.getTime(); date += 86400000L) {
            formattedDates.add(dateFormat.format(new java.sql.Date(date)));
        }

        request.setAttribute("dates", formattedDates);

        request.setAttribute("plan", plan);
        request.setAttribute("products", products);
        request.setAttribute("dates", formattedDates);
        request.getRequestDispatcher("/view/schedulecampaign/createsc.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        int plid = Integer.parseInt(request.getParameter("plid"));

        // Lấy Plan từ PlanDBContext
        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.getPlanById(plid);

        // Kiểm tra nếu Plan không tồn tại
        if (plan == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Plan not found with ID: " + plid);
            return;
        }

        // Lấy danh sách sản phẩm từ ProductDBContext
        ProductDBContext pDB = new ProductDBContext();
        ArrayList<Product> products = pDB.list();

        // Lấy danh sách PlanCampaign từ PlanID
        PlanCampaignDBContext planCampaignDB = new PlanCampaignDBContext();
        ArrayList<PlanCampaign> planCampaigns = planCampaignDB.getPlanCampaignsByPlanId(plid);

        // Duyệt qua các sản phẩm và ngày để lấy dữ liệu từ form
        String[] shifts = {"K1", "K2", "K3"};
        Date startDate = plan.getStartd();
        Date endDate = plan.getEndd();

        // Loop qua từng ngày và từng ca
        for (Product product : products) {
            PlanCampaign matchingPlanCampaign = planCampaigns.stream()
                    .filter(pc -> pc.getProduct().getpID() == product.getpID())
                    .findFirst().orElse(null);
            if (matchingPlanCampaign == null) {
                continue;
            }

            for (Date date = startDate; !date.after(endDate); date = new Date(date.getTime() + 86400000L)) {
                for (String shift : shifts) {
                    String quantityParam = request.getParameter("quantity_" + product.getpID() + "_" + shift + "_" + date);
                    if (quantityParam != null && !quantityParam.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityParam);

                            ScheduleCampaign schedule = new ScheduleCampaign();
                            schedule.setPlanCampaign(matchingPlanCampaign);
                            schedule.setDate(new java.sql.Date(date.getTime()));
                            schedule.setShift(shift);
                            schedule.setQuantity(quantity);

                            // Chèn dữ liệu vào ScheduleCampaignDBContext
                            scheduleDB.insert(schedule);
                        } catch (NumberFormatException e) {
                            // Bỏ qua nếu không thể chuyển đổi số lượng thành số nguyên
                        }
                    }
                }
            }
        }

        // Điều hướng lại danh sách hoặc trang xác nhận
        response.sendRedirect("../productionplan/list");
    }

}
