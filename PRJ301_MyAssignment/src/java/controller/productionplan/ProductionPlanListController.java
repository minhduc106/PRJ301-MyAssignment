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
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> plans = planDB.list();

        // Calculate status for each plan based on completed campaigns
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        for (Plan plan : plans) {
            ArrayList<ScheduleCampaign> completedCampaigns = scheduleDB.getScheduleCampaignsByPlanId(plan.getPlid());
            plan.calculateStatus(completedCampaigns);
        }

        request.setAttribute("plans", plans);
        request.getRequestDispatcher("/view/productionplan/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Currently, no operations are defined for POST in this controller.
    }
}
