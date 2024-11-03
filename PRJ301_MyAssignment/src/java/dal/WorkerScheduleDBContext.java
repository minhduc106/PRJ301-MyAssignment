package dal;

import java.sql.*;
import java.util.ArrayList;
import model.Employee;
import model.PlanCampaign;
import model.Product;
import model.ScheduleCampaign;
import model.WorkerSchedule;

public class WorkerScheduleDBContext extends DBContext<WorkerSchedule> {

    public ArrayList<WorkerSchedule> getWorkerSchedulesByDate(Date date, int did) {
        ArrayList<WorkerSchedule> workerSchedules = new ArrayList<>();
        String sql = "SELECT ws.wsid, ws.quantity, "
                + "e.eid, e.ename, "
                + "sc.scid, sc.date, sc.shift, "
                + "p.pid, p.pname "
                + "FROM WorkerSchedule ws "
                + "JOIN ScheduleCampaign sc ON ws.scid = sc.scid "
                + "JOIN Employee e ON ws.eid = e.eid "
                + "JOIN PlanCampaign pc ON sc.canid = pc.canid "
                + "JOIN Product p ON pc.pid = p.pid "
                + "WHERE sc.date = ? AND e.did = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, date);
            stm.setInt(2, did);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                WorkerSchedule ws = new WorkerSchedule();

                ws.setWsid(rs.getInt("wsid"));
                ws.setQuantity(rs.getInt("quantity"));

                ScheduleCampaign scheduleCampaign = new ScheduleCampaign();
                scheduleCampaign.setScid(rs.getInt("scid"));
                scheduleCampaign.setDate(rs.getDate("date"));
                scheduleCampaign.setShift(rs.getString("shift"));
                ws.setScheduleCampaign(scheduleCampaign);

                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                ws.setEmployee(employee);

                Product product = new Product();
                product.setpID(rs.getInt("pid"));
                product.setpName(rs.getString("pname"));

                PlanCampaign planCampaign = new PlanCampaign();
                planCampaign.setProduct(product);
                scheduleCampaign.setPlanCampaign(planCampaign);

                workerSchedules.add(ws);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return workerSchedules;
    }

    @Override
    public void insert(WorkerSchedule ws) {
        String sql = "INSERT INTO WorkerSchedule (scid, eid, quantity) VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, ws.getScheduleCampaign().getScid());
            stm.setString(2, ws.getEmployee().getEid());
            stm.setInt(3, ws.getQuantity());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(WorkerSchedule model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(WorkerSchedule model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<WorkerSchedule> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public WorkerSchedule get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}