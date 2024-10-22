/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Minh Duc
 */
public class Plan {

    private int plid;
    private Date startd;
    private Date endd;
    private Department department;
    
    
    private int totalQuantity;
    private int deliveredQuantity;
    private String status;

    private ArrayList<PlanCampaign> campaigns = new ArrayList<>();

    public ArrayList<PlanCampaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(ArrayList<PlanCampaign> campains) {
        this.campaigns = campains;
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

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String calculateStatus(Date currentDate, int totalQuantity, int deliveredQuantity) {
        if (endd.compareTo(currentDate) < 0 && deliveredQuantity < totalQuantity) {
            return "Late";
        } else if (deliveredQuantity >= totalQuantity) {
            return "Complete";
        } else {
            return "On-going";
        }
    }

    public void updateStatus(Date currentDate) {
        this.status = calculateStatus(currentDate, this.totalQuantity, this.deliveredQuantity);
    }
    

}
