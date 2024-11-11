package controller.productionplan;

import controller.accescontrol.BaseRBACController;
import dal.PlanDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import model.accesscontrol.User;

public class ProductionPlanScheduleController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        PlanDBContext dbContext = new PlanDBContext();
        ArrayList<String> monthsYears = dbContext.getAvailableMonthsAndYears();

        // Get selected month and year or fallback to first available option
        String monthYear = req.getParameter("monthYear");
        if (monthYear == null || monthYear.isEmpty()) {
            monthYear = monthsYears.isEmpty() ? "2024-10" : monthsYears.get(0);
        }
        
        req.setAttribute("selectedMonthYear", monthYear); // Ensure this is correctly passed to the JSP

        String[] parts = monthYear.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        YearMonth yearMonth = YearMonth.of(year, month);
        
        int daysInMonth = yearMonth.lengthOfMonth();
        int startDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue();

        req.setAttribute("monthsYears", monthsYears);
        req.setAttribute("daysInMonth", daysInMonth);
        req.setAttribute("startDayOfWeek", startDayOfWeek);

        req.getRequestDispatcher("/view/productionplan/schedule.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}