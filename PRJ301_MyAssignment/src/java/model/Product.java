/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Minh Duc
 */
public class Product {
    private int p_ID;
    private String p_Name;
    private double p_Effort;

    public Product() {
    }

    public Product(int p_ID, String p_Name, double p_Effort) {
        this.p_ID = p_ID;
        this.p_Name = p_Name;
        this.p_Effort = p_Effort;
    }

    public int getP_ID() {
        return p_ID;
    }

    public void setP_ID(int p_ID) {
        this.p_ID = p_ID;
    }

    public String getP_Name() {
        return p_Name;
    }

    public void setP_Name(String p_Name) {
        this.p_Name = p_Name;
    }

    public double getP_Effort() {
        return p_Effort;
    }

    public void setP_Effort(double p_Effort) {
        this.p_Effort = p_Effort;
    }
    
    
}
