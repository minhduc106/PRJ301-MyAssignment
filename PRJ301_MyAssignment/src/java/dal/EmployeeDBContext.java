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
public class EmployeeDBContext extends DBContext<Employee> {

    public ArrayList<Employee> search(Integer id, String name, Boolean gender, Date from, Date to, String address, Department dept) {

        return null;

    }

    @Override
    public void insert(Employee model) {

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

        return null;

    }

    @Override
    public Employee get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
