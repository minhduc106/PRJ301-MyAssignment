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
    private float estimatedeffort;
    private int madeQuantity;

    public float getEstimatedeffort() {
        return estimatedeffort;
    }

    public void setEstimatedeffort(float estimatedeffort) {
        this.estimatedeffort = estimatedeffort;
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

    public int getMadeQuantity() {
        return madeQuantity;
    }

    public void setMadeQuantity(int madeQuantity) {
        this.madeQuantity = madeQuantity;
    }

}
