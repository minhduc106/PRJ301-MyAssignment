package controller.productionplan;

import controller.accescontrol.BaseRBACController;
import dal.DepartmentDBContext;
import dal.ScheduleCampaignDBContext;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import model.ScheduleCampaign;
import model.accesscontrol.User;

public class ScheduleCampaignListController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int plid = Integer.parseInt(request.getParameter("plid"));
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        ArrayList<ScheduleCampaign> scheduleCampaigns = scheduleDB.getScheduleCampaignsByPlanId(plid);
        DepartmentDBContext dDB = new DepartmentDBContext();
        String departmentName = dDB.getDepartmentNameByPlanId(plid);
        request.setAttribute("departmentName", departmentName);

        request.setAttribute("scheduleCampaigns", scheduleCampaigns);
        request.setAttribute("plid", plid);
        request.getRequestDispatcher("/view/schedulecampaign/list.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
