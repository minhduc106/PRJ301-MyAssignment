package controller.attendance;

import controller.accescontrol.BaseRBACController;
import dal.AttendanceDBContext;
import dal.DepartmentDBContext;
import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import model.Attendance;
import model.Department;
import model.accesscontrol.User;

public class AttendanceDetailController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        // Lấy date và shift từ tham số yêu cầu
        Date date = Date.valueOf(req.getParameter("date")); // định dạng "yyyy-MM-dd"
        String shift = req.getParameter("shift");

        EmployeeDBContext edb = new EmployeeDBContext();
        int did = edb.getDepartmentIdByUsername(loggeduser.getUsername());
        DepartmentDBContext ddb = new DepartmentDBContext();
        Department dp = ddb.get(did);

        // Lấy dữ liệu Attendance
        AttendanceDBContext attendanceDB = new AttendanceDBContext();
        ArrayList<Attendance> attendances = attendanceDB.getAttendanceByDateAndShiftAndDid(date, shift, did);

        // Đặt các thuộc tính cho JSP
        req.setAttribute("dp", dp);
        req.setAttribute("date", date);
        req.setAttribute("shift", shift);
        req.setAttribute("attendances", attendances);

        // Chuyển hướng tới JSP để hiển thị
        req.getRequestDispatcher("/view/attendance/detail.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        // Không cần xử lý POST cho chức năng này
    }
}
