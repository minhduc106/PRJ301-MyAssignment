package controller.attendance;

import controller.accescontrol.BaseRBACController;
import dal.AttendanceDBContext;
import dal.WorkerScheduleDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Attendance;
import model.WorkerSchedule;
import model.accesscontrol.User;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import model.Employee;
import model.Product;

public class AttendanceCreateController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggedUser) throws ServletException, IOException {
        Date date = Date.valueOf(req.getParameter("date"));
        String shift = req.getParameter("shift");

        WorkerScheduleDBContext wsDB = new WorkerScheduleDBContext();
        Map<Employee, ArrayList<Product>> employeeProducts = wsDB.getDistinctEmployeesAndProductsByDateAndShift(date, shift);
        req.setAttribute("employeeProductMap", employeeProducts);

        req.setAttribute("date", date);
        req.setAttribute("shift", shift);

        req.getRequestDispatcher("/view/attendance/create.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggedUser) throws ServletException, IOException {
        String dateParam = req.getParameter("date");
        String shift = req.getParameter("shift");

        if (dateParam == null || shift == null || dateParam.isEmpty() || shift.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Date or shift parameter is missing.");
            return;
        }

        Date date = Date.valueOf(dateParam);
        AttendanceDBContext attendanceDB = new AttendanceDBContext();
        WorkerScheduleDBContext wsDB = new WorkerScheduleDBContext();

        // Iterate through request parameters
        for (String paramName : req.getParameterMap().keySet()) {
            if (paramName.startsWith("quantity_")) {
                String[] parts = paramName.split("_");
                String eid = parts[1];
                int productId = Integer.parseInt(parts[2]);
                String quantityStr = req.getParameter(paramName);

                if (quantityStr != null && !quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);

                    // Find the corresponding WorkerSchedule record
                    WorkerSchedule ws = wsDB.getWorkerSchedule(eid, productId, date, shift);
                    if (ws != null && ws.getQuantity() > 0) {
                        float alpha = (float) quantity / ws.getQuantity();

                        Attendance attendance = new Attendance();
                        attendance.setWorkerSchedule(ws);
                        attendance.setQuantity(quantity);
                        attendance.setAlpha(alpha);

                        // Insert the attendance record
                        boolean success = attendanceDB.add(attendance);
                        if (!success) {
                            System.out.println("Failed to insert attendance for employee: " + eid + ", product: " + productId);
                        }
                    }
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/attendance/detail?date=" + dateParam + "&shift=" + shift);
    }

}
