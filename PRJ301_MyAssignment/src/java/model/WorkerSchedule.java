/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Minh Duc
 */
public class WorkerSchedule {

    private int wsid;
    private ScheduleCampaign scheduleCampaign;
    private Employee employee;
    private int quantity;


    public int getWsid() {
        return wsid;
    }

    public void setWsid(int wsid) {
        this.wsid = wsid;
    }

    public ScheduleCampaign getScheduleCampaign() {
        return scheduleCampaign;
    }

    public void setScheduleCampaign(ScheduleCampaign scheduleCampaign) {
        this.scheduleCampaign = scheduleCampaign;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
