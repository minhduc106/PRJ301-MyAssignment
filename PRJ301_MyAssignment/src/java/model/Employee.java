/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Minh Duc
 */
public class Employee {
    private int eid;
    private String ename;
    private String salaryLevel;
    private Department department;

    public Employee(int eid, String ename, String salaryLevel, Department department) {
        this.eid = eid;
        this.ename = ename;
        this.salaryLevel = salaryLevel;
        this.department = department;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getSalaryLevel() {
        return salaryLevel;
    }

    public void setSalaryLevel(String salaryLevel) {
        this.salaryLevel = salaryLevel;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }   
}
