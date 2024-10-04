/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;

/**
 *
 * @author Minh Duc
 */
public class Plan {

    private int plid;
    private Date startd;
    private Date endd;
    private Department department;

    public Plan(int plid, Date startd, Date endd, Department department) {
        this.plid = plid;
        this.startd = startd;
        this.endd = endd;
        this.department = department;
    }

    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid = plid;
    }

    public Date getStartd() {
        return startd;
    }

    public void setStartd(Date startd) {
        this.startd = startd;
    }

    public Date getEndd() {
        return endd;
    }

    public void setEndd(Date endd) {
        this.endd = endd;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    
}
