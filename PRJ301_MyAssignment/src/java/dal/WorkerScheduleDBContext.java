package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import model.Employee;
import model.PlanCampaign;
import model.Product;
import model.ScheduleCampaign;
import model.WorkerSchedule;

public class WorkerScheduleDBContext extends DBContext<WorkerSchedule> {

    public WorkerSchedule getWorkerSchedule(String eid, int productId, Date date, String shift) {
        String sql = "SELECT * FROM WorkerSchedule ws "
                + "JOIN ScheduleCampaign sc ON ws.scid = sc.scid "
                + "JOIN PlanCampaign pc ON sc.canid = pc.canid "
                + "WHERE ws.eid = ? AND pc.pid = ? AND sc.date = ? AND sc.shift = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, eid);
            stm.setInt(2, productId);
            stm.setDate(3, date);
            stm.setString(4, shift);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                WorkerSchedule ws = new WorkerSchedule();
                ws.setWsid(rs.getInt("wsid"));
                ws.setQuantity(rs.getInt("quantity"));
                // Set other properties if necessary
                return ws;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Return null if not found or an error occurs
    }

    public Map<Employee, ArrayList<Product>> getDistinctEmployeesAndProductsByDateAndShift(Date date, String shift) {
        Map<Employee, ArrayList<Product>> employeeProductMap = new TreeMap<>(Comparator.comparing(Employee::getEid));
        String sql = "SELECT DISTINCT ws.eid, e.ename, p.pID, p.pName FROM WorkerSchedule ws "
                + "JOIN Employee e ON ws.eid = e.eid "
                + "JOIN ScheduleCampaign sc ON ws.scid = sc.scid "
                + "JOIN PlanCampaign pc ON sc.canid = pc.canid "
                + "JOIN Product p ON pc.pid = p.pID "
                + "WHERE sc.date = ? AND sc.shift = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, date);
            stm.setString(2, shift);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String eid = rs.getString("eid");
                String ename = rs.getString("ename");
                int pID = rs.getInt("pID");
                String pName = rs.getString("pName");

                Employee employee = new Employee();
                employee.setEid(eid);
                employee.setEname(ename);

                Product product = new Product();
                product.setpID(pID);
                product.setpName(pName);

                employeeProductMap.computeIfAbsent(employee, k -> new ArrayList<>()).add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employeeProductMap;
    }

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
