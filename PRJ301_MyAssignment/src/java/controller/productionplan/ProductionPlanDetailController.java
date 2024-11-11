package controller.productionplan;

import controller.accescontrol.BaseRBACController;
import dal.PlanDBContext;
import dal.ScheduleCampaignDBContext;
import model.Plan;
import model.PlanCampaign;
import model.ScheduleCampaign;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import model.accesscontrol.User;

public class ProductionPlanDetailController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int plid = Integer.parseInt(request.getParameter("plid"));
        
        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.get(plid);

        if (plan == null) {
            response.sendRedirect("/productionplan/list");
            return;
        }
        
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        ArrayList<ScheduleCampaign> completedCampaigns = scheduleDB.getScheduleCampaignsByPlanId(plid);

        // Tính toán trạng thái cho mỗi sản phẩm trong kế hoạch
        for (PlanCampaign campaign : plan.getCampaigns()) {
            int madeQuantity = completedCampaigns.stream()
                .filter(s -> s.getPlanCampaign().getCanid() == campaign.getCanid())
                .mapToInt(ScheduleCampaign::getQuantity)
                .sum();
            campaign.setMadeQuantity(madeQuantity);
        }

        request.setAttribute("plan", plan);
        request.getRequestDispatcher("/view/productionplan/detail.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}