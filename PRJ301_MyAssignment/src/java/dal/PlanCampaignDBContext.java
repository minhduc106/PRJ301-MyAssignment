package dal;

import model.PlanCampaign;
import model.Plan;
import model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlanCampaignDBContext extends DBContext<PlanCampaign> {
    
    
     public ArrayList<PlanCampaign> getPlanCampaignsByPlanId(int plid) {
        ArrayList<PlanCampaign> planCampaigns = new ArrayList<>();
        try {
            String sql = "SELECT pc.canid, pc.plid, pc.pid, pc.quantity, pc.estimatedeffort, " +
                         "p.pname " +
                         "FROM PlanCampaign pc " +
                         "INNER JOIN Product p ON pc.pid = p.pid " +
                         "WHERE pc.plid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, plid);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                PlanCampaign planCampaign = new PlanCampaign();
                planCampaign.setCanid(rs.getInt("canid"));

                // Thiết lập Plan cho PlanCampaign (chỉ đặt ID)
                Plan plan = new Plan();
                plan.setPlid(rs.getInt("plid"));
                planCampaign.setPlan(plan);

                // Thiết lập Product cho PlanCampaign
                Product product = new Product();
                product.setpID(rs.getInt("pid"));
                product.setpName(rs.getString("pname"));
                planCampaign.setProduct(product);

                planCampaign.setQuantity(rs.getInt("quantity"));
                planCampaign.setEstimatedeffort(rs.getFloat("estimatedeffort"));
                
                planCampaigns.add(planCampaign);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planCampaigns;
    }

    // Hàm lấy PlanCampaign dựa trên plan ID và product ID
    public PlanCampaign getPlanCampaignByPlanAndProduct(int plid, int pid) {
        String sql = "SELECT pc.canid, p.plid, p.startd, p.endd, pr.pid, pr.pname, pc.quantity, pc.estimatedeffort "
                   + "FROM PlanCampaign pc "
                   + "INNER JOIN Plan p ON pc.plid = p.plid "
                   + "INNER JOIN Product pr ON pc.pid = pr.pid "
                   + "WHERE pc.plid = ? AND pc.pid = ?";

        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
             
            ps.setInt(1, plid);
            ps.setInt(2, pid);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PlanCampaign planCampaign = new PlanCampaign();
                planCampaign.setCanid(rs.getInt("canid"));

                // Lấy thông tin Plan
                Plan plan = new Plan();
                plan.setPlid(rs.getInt("plid"));
                plan.setStartd(rs.getDate("startd"));
                plan.setEndd(rs.getDate("endd"));
                planCampaign.setPlan(plan);

                // Lấy thông tin Product
                Product product = new Product();
                product.setpID(rs.getInt("pid"));
                product.setpName(rs.getString("pname"));
                planCampaign.setProduct(product);

                // Các thuộc tính khác của PlanCampaign
                planCampaign.setQuantity(rs.getInt("quantity"));
                planCampaign.setEstimatedeffort(rs.getFloat("estimatedeffort"));

                return planCampaign;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Các hàm khác của DBContext có thể được kế thừa từ lớp cha, tùy theo nhu cầu

    @Override
    public void insert(PlanCampaign model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(PlanCampaign model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(PlanCampaign model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<PlanCampaign> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PlanCampaign get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
