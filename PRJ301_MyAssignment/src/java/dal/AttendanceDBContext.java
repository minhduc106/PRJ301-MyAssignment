package dal;

import java.sql.*;
import java.util.ArrayList;
import model.Attendance;
import model.Employee;
import model.PlanCampaign;
import model.Product;
import model.ScheduleCampaign;
import model.WorkerSchedule;

public class AttendanceDBContext extends DBContext<Attendance> {
    
    
    

    public boolean add(Attendance attendance) {
        String sql = "INSERT INTO Attendance (wsid, quantity, alpha) VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, attendance.getWorkerSchedule().getWsid()); // Correctly refer to the wsid in WorkerSchedule
            stm.setInt(2, attendance.getQuantity());
            stm.setDouble(3, attendance.getAlpha());
            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<Attendance> getAttendanceByDateAndShiftAndDid(Date date, String shift, int did) {
        ArrayList<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT a.aid,\n"
                + "       a.quantity AS actualQuantity,\n"
                + "       a.alpha,\n"
                + "       ws.wsid,\n"
                + "       ws.quantity AS orderedQuantity,\n"
                + "       e.eid,\n"
                + "       e.ename,\n"
                + "       p.pid,\n"
                + "       p.pname,\n"
                + "       sc.date,\n"
                + "       sc.shift\n"
                + "FROM Attendance a\n"
                + "JOIN WorkerSchedule ws ON a.wsid = ws.wsid\n"
                + "JOIN ScheduleCampaign sc ON ws.scid = sc.scid\n"
                + "JOIN Employee e ON ws.eid = e.eid\n"
                + "JOIN PlanCampaign pc ON sc.canid = pc.canid\n"
                + "JOIN Product p ON pc.pid = p.pid\n"
                + "WHERE sc.date = ? and sc.shift = ? and e.did = ?\n"
                + "ORDER BY e.eid";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, date);
            stm.setString(2, shift);
            stm.setInt(3, did);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                // Thiết lập thông tin Attendance
                Attendance attendance = new Attendance();
                attendance.setAid(rs.getInt("aid"));
                attendance.setQuantity(rs.getInt("actualQuantity"));
                attendance.setAlpha(rs.getFloat("alpha"));

                // Thiết lập thông tin WorkerSchedule
                WorkerSchedule workerSchedule = new WorkerSchedule();
                workerSchedule.setWsid(rs.getInt("wsid"));
                workerSchedule.setQuantity(rs.getInt("orderedQuantity"));

                // Thiết lập thông tin ScheduleCampaign
                ScheduleCampaign scheduleCampaign = new ScheduleCampaign();
                scheduleCampaign.setDate(rs.getDate("date"));
                scheduleCampaign.setShift(rs.getString("shift"));
                workerSchedule.setScheduleCampaign(scheduleCampaign);

                // Thiết lập thông tin Employee
                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                workerSchedule.setEmployee(employee);

                // Thiết lập thông tin Product
                Product product = new Product();
                product.setpID(rs.getInt("pid"));
                product.setpName(rs.getString("pname"));

                // Thiết lập Product cho PlanCampaign của ScheduleCampaign trong WorkerSchedule
                PlanCampaign planCampaign = new PlanCampaign();
                planCampaign.setProduct(product);
                scheduleCampaign.setPlanCampaign(planCampaign);

                // Thiết lập WorkerSchedule cho Attendance
                attendance.setWorkerSchedule(workerSchedule);

                attendances.add(attendance);
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
        return attendances;
    }

    @Override
    public void update(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Attendance> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Attendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
