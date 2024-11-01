package dal;

import model.Plan;
import model.Department;
import model.PlanCampaign;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanDBContext extends DBContext<Plan> {
    
    

    @Override
    public void insert(Plan model) {
        try {
            connection.setAutoCommit(false);

            // Insert vào bảng Plan
            String sql_insert_plan = "INSERT INTO [Plan] ([startd], [endd], [did]) VALUES (?, ?, ?)";
            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan, Statement.RETURN_GENERATED_KEYS);
            stm_insert_plan.setDate(1, model.getStartd());
            stm_insert_plan.setDate(2, model.getEndd());
            stm_insert_plan.setInt(3, model.getDepartment().getDid());
            stm_insert_plan.executeUpdate();

            // Lấy plid vừa được tạo
            ResultSet rs = stm_insert_plan.getGeneratedKeys();
            if (rs.next()) {
                model.setPlid(rs.getInt(1));
            }

            // Insert vào bảng PlanCampaign cho từng chiến dịch
            String sql_insert_campaign = "INSERT INTO [PlanCampaign] ([plid], [pid], [quantity], [estimatedeffort]) VALUES (?, ?, ?, ?)";
            for (PlanCampaign campaign : model.getCampaigns()) {
                PreparedStatement stm_insert_campaign = connection.prepareStatement(sql_insert_campaign);
                stm_insert_campaign.setInt(1, model.getPlid());
                stm_insert_campaign.setInt(2, campaign.getProduct().getpID());
                stm_insert_campaign.setInt(3, campaign.getQuantity());
                stm_insert_campaign.setFloat(4, campaign.getEstimatedeffort());
                stm_insert_campaign.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Plan model) {
        // Code update tương tự như insert nhưng sẽ dùng câu lệnh SQL UPDATE
    }

    @Override
    public void delete(Plan model) {
        // Code delete cho Plan
    }

    @Override
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        try {
            String sql = "SELECT p.plid, p.startd, p.endd, d.did, d.dname, d.dtype "
                    + "FROM [Plan] p "
                    + "JOIN Department d ON p.did = d.did";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Plan plan = new Plan();
                plan.setPlid(rs.getInt("plid"));
                plan.setStartd(rs.getDate("startd"));
                plan.setEndd(rs.getDate("endd"));

                Department department = new Department();
                department.setDid(rs.getInt("did"));
                department.setDname(rs.getString("dname"));
                department.setDtype(rs.getString("dtype"));
                plan.setDepartment(department);

                // Load PlanCampaigns cho mỗi Plan
                ArrayList<PlanCampaign> campaigns = getCampaignsByPlanId(plan.getPlid());
                plan.setCampaigns(campaigns);

                plans.add(plan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return plans;
    }

    @Override
    public Plan get(int id) {
        Plan plan = null;
        try {
            String sql = "SELECT p.plid, p.startd, p.endd, p.did, d.dname, d.dtype, "
                    + "pc.canid, pc.pid, pc.quantity, pc.estimatedeffort, prod.pname "
                    + "FROM [Plan] p "
                    + "LEFT JOIN PlanCampaign pc ON p.plid = pc.plid "
                    + "LEFT JOIN Department d ON p.did = d.did "
                    + "LEFT JOIN Product prod ON pc.pid = prod.pid "
                    + "WHERE p.plid = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                if (plan == null) {
                    plan = new Plan();
                    plan.setPlid(rs.getInt("plid"));
                    plan.setStartd(rs.getDate("startd"));
                    plan.setEndd(rs.getDate("endd"));

                    Department dept = new Department();
                    dept.setDid(rs.getInt("did"));
                    dept.setDname(rs.getString("dname"));
                    dept.setDtype(rs.getString("dtype"));
                    plan.setDepartment(dept);
                }

                // Tạo PlanCampaign và thiết lập quan hệ với Plan
                PlanCampaign campaign = new PlanCampaign();
                campaign.setCanid(rs.getInt("canid"));
                campaign.setQuantity(rs.getInt("quantity"));
                campaign.setEstimatedeffort(rs.getFloat("estimatedeffort"));

                Product product = new Product();
                product.setpID(rs.getInt("pid"));
                product.setpName(rs.getString("pname"));
                campaign.setProduct(product);

                // Thiết lập đối tượng Plan trong PlanCampaign để tránh lỗi null
                campaign.setPlan(plan);

                plan.getCampaigns().add(campaign);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return plan;
    }

    private ArrayList<PlanCampaign> getCampaignsByPlanId(int plid) {
    ArrayList<PlanCampaign> campaigns = new ArrayList<>();
    try {
        String sql = "SELECT canid, pid, quantity, estimatedeffort "
                   + "FROM [PlanCampaign] WHERE plid = ?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, plid);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            PlanCampaign campaign = new PlanCampaign();
            campaign.setCanid(rs.getInt("canid"));
            campaign.setQuantity(rs.getInt("quantity"));
            campaign.setEstimatedeffort(rs.getFloat("estimatedeffort"));

            Product product = new Product();
            product.setpID(rs.getInt("pid"));
            campaign.setProduct(product);

            // Đặt Plan cho mỗi PlanCampaign
            Plan plan = new Plan();
            plan.setPlid(plid);
            campaign.setPlan(plan);

            campaigns.add(campaign);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return campaigns;
}

}
