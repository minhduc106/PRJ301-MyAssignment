/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.accesscontrol.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.accesscontrol.Feature;
import model.accesscontrol.Role;

/**
 *
 * @author sonnt-local
 */
public class UserDBContext extends DBContext<User> {

    public ArrayList<Role> getRoles(String username) {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "SELECT r.roleId, r.roleName, f.featureId, f.featureName, f.url\n"
                + "FROM [User] u\n"
                + "INNER JOIN UserRole ur ON ur.username = u.username\n"
                + "INNER JOIN [Role] r ON r.roleId = ur.roleId\n"
                + "INNER JOIN RoleFeature rf ON rf.roleId = r.roleId\n"
                + "INNER JOIN Feature f ON f.featureId = rf.featureId\n"
                + "WHERE u.username = ?\n"
                + "ORDER BY r.roleId ASC, f.featureId ASC;";

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            Role crole = new Role();
            crole.setId(-1);
            while (rs.next()) {
                int rid = rs.getInt("roleId");
                if (rid != crole.getId()) {
                    crole = new Role();
                    crole.setId(rid);
                    crole.setName(rs.getString("roleName"));
                    roles.add(crole);
                }
                Feature f = new Feature();
                f.setId(rs.getInt("featureId"));
                f.setName(rs.getString("featureName"));
                f.setUrl(rs.getString("url"));
                crole.getFeatures().add(f);
                f.setRoles(roles);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return roles;
    }

    public User get(String username, String password) {
        String sql = "SELECT [username]\n"
                + "    ,[password]\n"
                + "    ,[displayname]\n"
                + "FROM [User]\n"
                + "WHERE username = ? AND [password] = ?";

        PreparedStatement stm = null;
        User user = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setDisplayname(rs.getString("displayname"));
                user.setUsername(username);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<User> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
