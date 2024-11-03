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
import model.ScheduleCampaign;
import model.WorkerSchedule;
import model.accesscontrol.User;

public class PersonalDevelopmentDetailController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        Date date = Date.valueOf(req.getParameter("date"));

        ScheduleCampaignDBContext scheduleDB = new ScheduleCampaignDBContext();
        ScheduleCampaign scheduleCampaign = scheduleDB.getScheduleByDate(date);
        if (scheduleCampaign == null) {
            req.setAttribute("message", "No personnel deployment plan exists for " + date + ".");
        } else {
            String shift = scheduleCampaign.getShift();
            EmployeeDBContext edb = new EmployeeDBContext();
            WorkerScheduleDBContext workerScheduleDB = new WorkerScheduleDBContext();
            int did = edb.getDepartmentIdByUsername(loggeduser.getUsername());
            ArrayList<WorkerSchedule> workerSchedules = workerScheduleDB.getWorkerSchedulesByDate(date, did);

            req.setAttribute("date", date);
            req.setAttribute("shift", shift);
            req.setAttribute("workerSchedules", workerSchedules);
        }

        req.getRequestDispatcher("/view/personaldevelopment/detail.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
    }
}
