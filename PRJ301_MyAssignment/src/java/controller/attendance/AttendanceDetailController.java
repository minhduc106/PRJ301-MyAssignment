package controller.attendance;

import controller.accescontrol.BaseRBACController;
import dal.AttendanceDBContext;
import dal.DepartmentDBContext;
import dal.EmployeeDBContext;
import dal.ScheduleCampaignDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Attendance;
import model.Department;
import model.ScheduleCampaign;
import model.accesscontrol.User;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class AttendanceDetailController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        // Lấy ngày từ tham số yêu cầu
        String dateParam = req.getParameter("date");
        Date date = null;
        if (dateParam != null && !dateParam.isEmpty()) {
            date = Date.valueOf(dateParam);
        }
        req.setAttribute("date", date);

        // Lấy ID phòng ban từ tài khoản người dùng đã đăng nhập
        EmployeeDBContext edb = new EmployeeDBContext();
        int did = edb.getDepartmentIdByUsername(loggeduser.getUsername());

        // Lấy thông tin phòng ban
        DepartmentDBContext ddb = new DepartmentDBContext();
        Department dp = ddb.get(did);
        req.setAttribute("dp", dp);

        // Lấy danh sách các ca làm việc dựa trên ngày đã chọn
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        ArrayList<ScheduleCampaign> shifts = scheduleDB.getShiftsByDate(date);
        req.setAttribute("shifts", shifts);

        // Lấy dữ liệu điểm danh theo ngày và ca làm việc
        String shift = req.getParameter("shift");
        if (shift != null && date != null) {
            AttendanceDBContext attendanceDB = new AttendanceDBContext();
            ArrayList<Attendance> attendances = attendanceDB.getAttendanceByDateAndShiftAndDid(date, shift, did);
            req.setAttribute("attendances", attendances);
            req.setAttribute("shift", shift);
        }

        // Chuyển hướng tới JSP để hiển thị
        req.getRequestDispatcher("/view/attendance/detail.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        // Không cần xử lý POST cho chức năng này
    }
}
