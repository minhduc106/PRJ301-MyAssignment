/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Minh Duc
 */
public class Attendance {
    private int aid;
    private WorkerSchedule workerSchedule;
    private int quantity;
    private float alpha;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public WorkerSchedule getWorkerSchedule() {
        return workerSchedule;
    }

    public void setWorkerSchedule(WorkerSchedule workerSchedule) {
        this.workerSchedule = workerSchedule;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    
}
