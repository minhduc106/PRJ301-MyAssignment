package controller.employee;

import controller.accescontrol.BaseRBACController;
import dal.DepartmentDBContext;
import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Department;
import model.Employee;
import model.accesscontrol.User;

public class EmployeeListController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {

        EmployeeDBContext db = new EmployeeDBContext();
        ArrayList<Employee> employees = db.listByLoggedUser(loggeduser);
        
        DepartmentDBContext ddb = new DepartmentDBContext();
        int departmentId = db.getDepartmentIdByUsername(loggeduser.getUsername());
        Department department = ddb.get(departmentId); // Lưu kết quả vào một biến

        req.setAttribute("department", department); // Truyền đối tượng Department cho JSP
        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/view/employee/list.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        // Not needed for listing functionality
    }
}
