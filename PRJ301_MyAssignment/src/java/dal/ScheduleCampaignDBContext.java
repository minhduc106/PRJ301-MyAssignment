package dal;

import model.ScheduleCampaign;
import model.PlanCampaign;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import model.Product;

public class ScheduleCampaignDBContext extends DBContext<ScheduleCampaign> {

    // Lấy danh sách tất cả ScheduleCampaign từ database
    @Override
    public ArrayList<ScheduleCampaign> list() {
        ArrayList<ScheduleCampaign> scheduleCampaigns = new ArrayList<>();
        String sql = "SELECT sc.scid, sc.date, sc.shift, sc.quantity, "
                + "pc.canid, pc.quantity, pc.estimatedeffort, "
                + "p.pid, p.pname, pl.plid, pl.startd, pl.endd "
                + "FROM ScheduleCampaign sc "
                + "INNER JOIN PlanCampaign pc ON sc.canid = pc.canid "
                + "INNER JOIN Product p ON pc.pid = p.pid "
                + "INNER JOIN [Plan] pl ON pc.plid = pl.plid";
        try (
                PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                ScheduleCampaign sc = new ScheduleCampaign();
                sc.setScid(rs.getInt("scid"));
                sc.setDate(rs.getDate("date"));
                sc.setShift(rs.getString("shift"));
                sc.setQuantity(rs.getInt("quantity"));

                PlanCampaign pc = new PlanCampaign();
                pc.setCanid(rs.getInt("canid"));
                pc.setQuantity(rs.getInt("quantity"));
                pc.setEstimatedeffort(rs.getFloat("estimatedeffort"));

                sc.setPlanCampaign(pc);
                scheduleCampaigns.add(sc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleCampaigns;
    }

    public ArrayList<ScheduleCampaign> getScheduleCampaignsByPlanId(int plid) {
        ArrayList<ScheduleCampaign> scheduleCampaigns = new ArrayList<>();
        try {
            String sql = "SELECT sc.date, sc.shift, sc.quantity, p.pName FROM ScheduleCampaign sc "
                    + "JOIN PlanCampaign pc ON sc.canid = pc.canid "
                    + "JOIN Product p ON pc.pid = p.pid "
                    + "WHERE pc.plid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, plid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ScheduleCampaign sc = new ScheduleCampaign();
                sc.setDate(rs.getDate("date"));
                sc.setShift(rs.getString("shift"));
                sc.setQuantity(rs.getInt("quantity"));

                // Create and set PlanCampaign and Product
                PlanCampaign pc = new PlanCampaign();
                Product p = new Product();
                p.setpName(rs.getString("pName"));
                pc.setProduct(p);
                sc.setPlanCampaign(pc);

                scheduleCampaigns.add(sc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleCampaigns;
    }

    // Lấy danh sách ScheduleCampaign theo PlanCampaign ID
    public ArrayList<ScheduleCampaign> getScheduleByPlanCampaign(int canid) {
        ArrayList<ScheduleCampaign> scheduleCampaigns = new ArrayList<>();
        String sql = "SELECT scid, date, shift, quantity "
                + "FROM ScheduleCampaign WHERE canid = ?";
        try (
                PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, canid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ScheduleCampaign sc = new ScheduleCampaign();
                sc.setScid(rs.getInt("scid"));
                sc.setDate(rs.getDate("date"));
                sc.setShift(rs.getString("shift"));
                sc.setQuantity(rs.getInt("quantity"));

                PlanCampaign pc = new PlanCampaign();
                pc.setCanid(canid);
                sc.setPlanCampaign(pc);

                scheduleCampaigns.add(sc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleCampaigns;
    }

    // Thêm mới một ScheduleCampaign
    @Override
    public void insert(ScheduleCampaign sc) {
        String sql = "INSERT INTO ScheduleCampaign (canid, date, shift, quantity) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, sc.getPlanCampaign().getCanid());
            stm.setDate(2, new Date(sc.getDate().getTime()));
            stm.setString(3, sc.getShift());
            stm.setInt(4, sc.getQuantity());

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin ScheduleCampaign
    @Override
    public void update(ScheduleCampaign sc) {
        String sql = "UPDATE ScheduleCampaign SET canid = ?, date = ?, shift = ?, quantity = ? WHERE scid = ?";
        try (
                PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, sc.getPlanCampaign().getCanid());
            stm.setDate(2, new Date(sc.getDate().getTime()));
            stm.setString(3, sc.getShift());
            stm.setInt(4, sc.getQuantity());
            stm.setInt(5, sc.getScid());

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa một ScheduleCampaign theo ID
    @Override
    public void delete(ScheduleCampaign sc) {
        String sql = "DELETE FROM ScheduleCampaign WHERE scid = ?";
        try (
                PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, sc.getScid());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy ScheduleCampaign bằng ID
    @Override
    public ScheduleCampaign get(int scid) {
        String sql = "SELECT scid, canid, date, shift, quantity FROM ScheduleCampaign WHERE scid = ?";
        try (
                PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, scid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                ScheduleCampaign sc = new ScheduleCampaign();
                sc.setScid(rs.getInt("scid"));
                sc.setDate(rs.getDate("date"));
                sc.setShift(rs.getString("shift"));
                sc.setQuantity(rs.getInt("quantity"));

                PlanCampaign pc = new PlanCampaign();
                pc.setCanid(rs.getInt("canid"));
                sc.setPlanCampaign(pc);

                return sc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
