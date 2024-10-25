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
public class ScheduleCampaign {
    private int scid;
    private PlanCampaign planCampaign;
    private Date date;
    private String shift;
    private int quantity;


    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }

    public PlanCampaign getPlanCampaign() {
        return planCampaign;
    }

    public void setPlanCampaign(PlanCampaign planCampaign) {
        this.planCampaign = planCampaign;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
