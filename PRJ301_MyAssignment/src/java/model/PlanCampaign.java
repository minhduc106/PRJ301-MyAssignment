/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Minh Duc
 */
public class PlanCampaign {

    private int canid;
    private Plan plan;
    private Product product;
    private int quantity;

    public PlanCampaign(int canid, Plan plan, Product product, int quantity) {
        this.canid = canid;
        this.plan = plan;
        this.product = product;
        this.quantity = quantity;
    }

    public int getCanid() {
        return canid;
    }

    public void setCanid(int canid) {
        this.canid = canid;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
