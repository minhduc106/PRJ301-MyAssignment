package controller.productionplan;

import dal.DepartmentDBContext;
import dal.ScheduleCampaignDBContext;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import model.ScheduleCampaign;

public class ScheduleCampaignListController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
