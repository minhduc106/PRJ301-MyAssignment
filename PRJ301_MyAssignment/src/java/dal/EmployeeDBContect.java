package dal;

import java.util.ArrayList;
import model.Employee;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Minh Duc
 */
public class EmployeeDBContect extends DBContext<Employee> {

    @Override
    public void insert(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Employee> list() {
        ArrayList<Employee> emps = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String sql = "select e.eid, e.ename, e.salaryLevel, d.did, d.dname, d.dtype\n"
                    + "from Employee e join Department d on e.did = d.did";

            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Department d = new Department(rs.getInt("did"), rs.getNString("dname"), rs.getNString("dtype"));
                Employee e = new Employee(rs.getInt("eid"), rs.getNString("ename"), rs.getNString("salarylevel"), d);
                emps.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return emps;
    }

    @Override
    public Employee get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
