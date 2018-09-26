/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation;

import db.DBUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Department;
import objects.User;
import session.SessionUtil;

/**
 *
 * @author Hp
 */
public class SessionBean {
/*********************************DEPARTMENTS*************************************/
    public ArrayList<Department> getDepartment(String filter) {
        ArrayList<Department> list = new ArrayList();
        try {
            Connection connection = DBUtil.getConnection();

            String sql = "SELECT d.code, d.name name, d.head, concat(u.name, ' ', u.family) headname, d.activated FROM "
                    + " department d, user u where u.code = d.head"
                    + " and (? or (not ? and d.head = ?)) "
                    + " and (d.code = ? or ? is  null)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, "A".equalsIgnoreCase(SessionUtil.getUserRole()));
            ps.setBoolean(2, "A".equalsIgnoreCase(SessionUtil.getUserRole()));
            ps.setString(3, SessionUtil.getUserCode());
            ps.setString(4, filter);
            ps.setString(5, filter);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String code = rs.getString(1);
                String name = rs.getString(2);
                String head = rs.getString(3);
                String headName = rs.getString(4);
                Boolean active = rs.getBoolean(5);
                Department b = new Department(code, name, head, headName, active);
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean manageDepartment(Department d, String action) {
        Connection conn = DBUtil.getConnection();
        String sql = "";
        if ("U".equals(action)) {
            sql = "UPDATE department SET name=?, head= ?, updated_date = current_date, updated_by =? WHERE code=?";
        } else if ("C".equals(action)) {
            sql = "Insert into department (name, head, created_date, created_by, code) values(?,?,current_date,?,?)";
        }
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, d.getName());
            stmt.setString(2, d.getHead());
            stmt.setString(3, SessionUtil.getUserCode());
            stmt.setString(4, d.getCode());
            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
/*********************************Degree*************************************/
     public ArrayList<Department> getDegree(String filter, String department) {
        ArrayList<Department> list = new ArrayList();
        try {
            Connection connection = DBUtil.getConnection();

            String sql = "SELECT d.code, d.name name, de.code department ,de.name departmentName, d.credits, d.price"
                    + "d.activated  "
                    + " FROM degree d, department de "
                    + " where de.code = d.department"
                    + " and (? or (not ? and de.head = ?)) "
                    + " and (d.code = ? or ? is  null)"
                    + " and (d.department = ? or ? is  null)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, "A".equalsIgnoreCase(SessionUtil.getUserRole()));
            ps.setBoolean(2, "A".equalsIgnoreCase(SessionUtil.getUserRole()));
            ps.setString(3, SessionUtil.getUserCode());
            ps.setString(4, filter);
            ps.setString(5, filter);
            ps.setString(6, department);
            ps.setString(7, department);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String code = rs.getString(1);
                String name = rs.getString(2);
                String head = rs.getString(3);
                String headName = rs.getString(4);
                Boolean active = rs.getBoolean(5);
                Department b = new Department(code, name, head, headName, active);
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }   
/*********************************OTHER*************************************/
    public boolean delete(String table, String code) {
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("Delete from "+table+" where code = ?");
            stmt.setString(1, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean actdeac(String table, String code, boolean action) {
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("Update "+table+" set activated = ? where code = ?");
            stmt.setString(1, code);
            stmt.setBoolean(2, action);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }    
    
    public ArrayList<User> getUsers(String role, boolean all) {
        ArrayList<User> list = new ArrayList();
        try {
            Connection connection = DBUtil.getConnection();

            String sql = "SELECT u.code, concat(u.name, ' ', u.family) name, u.role, r.name, u.username, u.pass, u.activated FROM "
                    + " user u, role r"
                    + " where u.role = r.code "
                    + " and (activated or ?)"
                    + " and u.role = ? ";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, all);
            ps.setString(2, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User b = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getBoolean(7));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
