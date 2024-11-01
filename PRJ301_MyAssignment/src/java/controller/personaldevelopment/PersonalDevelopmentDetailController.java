package controller.personaldevelopment;

import controller.accescontrol.BaseRBACController;
import dal.EmployeeDBContext;
import dal.ProductDBContext;
import dal.ScheduleCampaignDBContext;
import dal.WorkerScheduleDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import model.Product;
import model.ScheduleCampaign;
import model.WorkerSchedule;
import model.accesscontrol.User;

public class PersonalDevelopmentDetailController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        // Lấy date từ tham số yêu cầu
        Date date = Date.valueOf(req.getParameter("date")); // date phải là "yyyy-MM-dd"

        // Lấy ScheduleCampaign dựa trên date để lấy thông tin shift
        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        ScheduleCampaign scheduleCampaign = scheduleDB.getScheduleByDate(date);
        String shift = scheduleCampaign.getShift(); // Lấy ca làm việc từ ScheduleCampaign
        EmployeeDBContext edb = new EmployeeDBContext();
        // Lấy danh sách WorkerSchedule (dựa vào date)
        WorkerScheduleDBContext workerScheduleDB = new WorkerScheduleDBContext();
        int did = edb.getDepartmentIdByUsername(loggeduser.getUsername());
        ArrayList<WorkerSchedule> workerSchedules = workerScheduleDB.getWorkerSchedulesByDate(date,did);

        // Đặt các thuộc tính cho JSP
        req.setAttribute("date", date);
        req.setAttribute("shift", shift); // Truyền shift cho JSP
        req.setAttribute("workerSchedules", workerSchedules);

        // Chuyển hướng tới JSP để hiển thị
        req.getRequestDispatcher("/view/personaldevelopment/detail.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        // Không cần xử lý POST cho chức năng này
    }
}
