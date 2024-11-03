package dal;

import model.ScheduleCampaign;
import model.PlanCampaign;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import model.Plan;
import model.Product;

public class ScheduleCampaignDBContext extends DBContext<ScheduleCampaign> {

    public int getScheduleCampaignId(int planId, String shift, int productId, Date date) {
        String sql = "SELECT sc.scid FROM ScheduleCampaign sc "
                + "JOIN PlanCampaign pc ON sc.canid = pc.canid "
                + "WHERE pc.plid = ? AND sc.shift = ? AND pc.pid = ? AND sc.date = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planId);
            stm.setString(2, shift);
            stm.setInt(3, productId);
            stm.setDate(4, date);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("scid");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1; // Indicate no match found
    }

    public ArrayList<ScheduleCampaign> getDistinctShiftsForPlanAndDate(int planId, Date date) {
        ArrayList<ScheduleCampaign> shifts = new ArrayList<>();
        String sql = "SELECT DISTINCT [shift] "
                + "FROM [ScheduleCampaign] "
                + "WHERE [canid] IN (SELECT [canid] FROM [PlanCampaign] WHERE [plid] = ?) "
                + "AND [date] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planId);
            stm.setDate(2, date);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ScheduleCampaign sc = new ScheduleCampaign();
                sc.setShift(rs.getString("shift"));
                shifts.add(sc);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return shifts;
    }

    public ArrayList<ScheduleCampaign> getScheduleCampaignsForMonth(Date date) {
        ArrayList<ScheduleCampaign> scheduleCampaigns = new ArrayList<>();
        String sql = "SELECT sc.[scid], sc.[date], sc.[shift], sc.[quantity], "
                + "pc.[plid], p.[pname] "
                + "FROM [ScheduleCampaign] sc "
                + "JOIN [PlanCampaign] pc ON sc.[canid] = pc.[canid] "
                + "JOIN [Product] p ON pc.[pid] = p.[pid] "
                + "WHERE MONTH(sc.[date]) = MONTH(?) AND YEAR(sc.[date]) = YEAR(?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, date);
            stm.setDate(2, date);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ScheduleCampaign sc = new ScheduleCampaign();
                sc.setScid(rs.getInt("scid"));
                sc.setDate(rs.getDate("date"));
                sc.setShift(rs.getString("shift"));
                sc.setQuantity(rs.getInt("quantity"));

                PlanCampaign pc = new PlanCampaign();
                Plan pl = new Plan();
                pl.setPlid(rs.getInt("plid"));
                pc.setPlan(pl);

                Product product = new Product();
                product.setpName(rs.getString("pname"));
                pc.setProduct(product);

                sc.setPlanCampaign(pc);
                scheduleCampaigns.add(sc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleCampaigns;
    }

    public ScheduleCampaign getScheduleByDate(Date date) {
        ScheduleCampaign scheduleCampaign = null;
        String sql = "SELECT scid, shift FROM ScheduleCampaign WHERE date = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, date);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                scheduleCampaign = new ScheduleCampaign();
                scheduleCampaign.setScid(rs.getInt("scid"));
                scheduleCampaign.setShift(rs.getString("shift"));
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
        return scheduleCampaign;
    }

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
        ArrayList<ScheduleCampaign> campaigns = new ArrayList<>();
        String sql = "SELECT sc.scid, sc.date, sc.shift, sc.quantity, "
                + "pc.canid, p.pid, p.pname "
                + "FROM ScheduleCampaign sc "
                + "JOIN PlanCampaign pc ON sc.canid = pc.canid "
                + "JOIN Product p ON pc.pid = p.pid "
                + "WHERE pc.plid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, plid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ScheduleCampaign campaign = new ScheduleCampaign();
                campaign.setScid(rs.getInt("scid"));
                campaign.setDate(rs.getDate("date"));
                campaign.setShift(rs.getString("shift"));
                campaign.setQuantity(rs.getInt("quantity"));

                PlanCampaign planCampaign = new PlanCampaign();
                planCampaign.setCanid(rs.getInt("canid"));

                Product product = new Product();
                product.setpID(rs.getInt("pid"));
                product.setpName(rs.getString("pname"));
                planCampaign.setProduct(product);

                campaign.setPlanCampaign(planCampaign);
                campaigns.add(campaign);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return campaigns;
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
