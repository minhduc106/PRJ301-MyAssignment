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
    private String e_ID;
    private String e_Name;
    private String e_Level;
    private int e_HourlyRate;

    public Employee() {
    }

    public Employee(String e_ID, String e_Name, String e_Level, int e_HourlyRate) {
        this.e_ID = e_ID;
        this.e_Name = e_Name;
        this.e_Level = e_Level;
        this.e_HourlyRate = e_HourlyRate;
    }

    public String getE_ID() {
        return e_ID;
    }

    public void setE_ID(String e_ID) {
        this.e_ID = e_ID;
    }

    public String getE_Name() {
        return e_Name;
    }

    public void setE_Name(String e_Name) {
        this.e_Name = e_Name;
    }

    public String getE_Level() {
        return e_Level;
    }

    public void setE_Level(String e_Level) {
        this.e_Level = e_Level;
    }

    public int getE_HourlyRate() {
        return e_HourlyRate;
    }

    public void setE_HourlyRate(int e_HourlyRate) {
        this.e_HourlyRate = e_HourlyRate;
    }
    
    
}
