package controller.productionplan;

import dal.PlanDBContext;
import dal.ScheduleCampaignDBContext;
import model.Plan;
import model.ScheduleCampaign;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

public class ProductionPlanListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null || action.equals("list")) {
            listPlans(request, response);
        } else if (action.equals("detail")) {
            planDetail(request, response);
        }
    }

    private void listPlans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> plans = planDB.list();

        // Kiểm tra trạng thái của từng Plan dựa trên các điều kiện của ScheduleCampaign
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        for (Plan plan : plans) {
            ArrayList<ScheduleCampaign> completedCampaigns = scheduleDB.getScheduleCampaignsByPlanId(plan.getPlid());
            plan.calculateStatus(completedCampaigns); // Cập nhật trạng thái của từng kế hoạch dựa trên các chiến dịch hoàn thành
        }

        request.setAttribute("plans", plans);
        request.getRequestDispatcher("/view/productionplan/list.jsp").forward(request, response);
    }

    private void planDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int plid = Integer.parseInt(request.getParameter("plid"));

        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.get(plid);

        if (plan == null) {
            response.sendRedirect("list");
            return;
        }

        // Lấy tất cả các chiến dịch đã hoàn thành (ScheduleCampaign) liên quan đến Plan này
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        ArrayList<ScheduleCampaign> completedCampaigns = scheduleDB.getScheduleCampaignsByPlanId(plid);
        
        // Tính toán và cập nhật trạng thái của Plan
        plan.calculateStatus(completedCampaigns);

        request.setAttribute("plan", plan);
        request.setAttribute("completedCampaigns", completedCampaigns);
        request.getRequestDispatcher("/view/productionplan/detail.jsp").forward(request, response);
    }
}
