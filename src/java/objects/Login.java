/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import org.apache.catalina.manager.util.SessionUtils;
import session.SessionUtil;

/**
 *
 * @author Hp
 */
public class Login {

    public static boolean validate(String user, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement("Select username, name, role, code from user where username = ? and pass = ?");
            HttpSession session = SessionUtil.getSession();
            ps.setString(1, user);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            //
            if (rs.next()) {
                session.setAttribute("username", user);
                session.setAttribute("name", rs.getString(2));
                session.setAttribute("role", rs.getString(3));
                session.setAttribute("code", rs.getString(4));
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        }
        return false;
    }

}
