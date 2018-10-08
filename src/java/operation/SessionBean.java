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
import objects.*;
import session.SessionUtil;

/**
 *
 * @author Hp
 */
public class SessionBean {

    Connection connection = DBUtil.getConnection();

    /**
     * *******************************DEPARTMENTS************************************
     */
    public ArrayList<Department> getDepartment(String filter) {
        ArrayList<Department> list = new ArrayList();
        try {

            String sql = "SELECT d.code, d.name name, d.head, concat(u.name, ' ', u.family) headname, d.activated FROM "
                    + " department d, user u where u.code = d.head"
                    + " and (not ? or ( ? and d.head = ?)) "
                    + " and (d.code = ? or ? is  null)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, SessionUtil.isHead());
            ps.setBoolean(2, SessionUtil.isHead());
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

    public boolean manageDepartment(Department d, String action) throws SQLException {
        String sql = "";
        if ("U".equals(action)) {
            sql = "UPDATE department SET name=?, head= ?, updated_date = current_date, updated_by =? WHERE code=?";
        } else if ("C".equals(action)) {
            sql = "Insert into department (name, head, created_date, created_by, code) values(?,?,current_date,?,?)";
        }

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, d.getName());
        stmt.setString(2, d.getHead());
        stmt.setString(3, SessionUtil.getUserCode());
        stmt.setString(4, d.getCode());
        return stmt.executeUpdate() > 0;

    }

    /**
     * *******************************Degree************************************
     */
    public ArrayList<Degree> getDegree(String filter, String department) {
        ArrayList<Degree> list = new ArrayList();
        try {
            String sql = "SELECT d.code, d.name name, de.code department ,de.name departmentName, d.credits, d.price,"
                    + "d.activated  "
                    + " FROM degree d, department de "
                    + " where de.code = d.department"
                    + " and (not ? or (? and de.head = ?)) "
                    + " and (d.code = ? or ? is  null)"
                    + " and (d.department = ? or ? is  null)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, SessionUtil.isHead());
            ps.setBoolean(2, SessionUtil.isHead());
            ps.setString(3, SessionUtil.getUserCode());
            ps.setString(4, filter);
            ps.setString(5, filter);
            ps.setString(6, department);
            ps.setString(7, department);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Degree d = new Degree(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getDouble(6), rs.getBoolean(7));
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ****************************DegreesCourse********************************
     */
    public ArrayList<DegreeCourse> getDegreeCourse(String degree) {
        ArrayList<DegreeCourse> list = new ArrayList();
        try {
            String sql = "Select DC.degree, DC.course, DC.semester, C.credits, D.name, C.name"
                    + "     From degree d, course c, degreecourse dc"
                    + "    where dc.degree = d.code"
                    + "      and dc.course = c.code "
                    + "      and dc.degree = ?"
                    + "      order by semester";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, degree);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DegreeCourse d = new DegreeCourse(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                list.add(d);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Course> getCoursesForDegee(String degree) {
        ArrayList<Course> list = new ArrayList();
        try {
            String sql = "Select C.code, C.name"
                    + "     From course c"
                    + "    where activated"
                    + "      and c.code not in(select course from degreecourse where degree = ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, degree);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course(rs.getString(1), rs.getString(2), 0, null);
                list.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void manageDegreeCourse(DegreeCourse dc, String action) {
        String sql = "";
        boolean create = false;
        if ("D".equalsIgnoreCase(action)) {
            sql = "Delete from degreecourse where degree = ? and course = ?";
        } else {
            create = true;
            sql = "Insert into degreecourse (degree, course, semester) values "
                    + "(?, ?, ?)";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, dc.getDegree());
            ps.setString(2, dc.getCourse());
            if (create) {
                ps.setInt(3, dc.getSemester());
            }

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * ********************************USER*************************************
     */
    public ArrayList<User> getUsers(String role, boolean all, String code, String name) {
        ArrayList<User> list = new ArrayList();
        try {
            String sql = "SELECT u.code, u.role, r.name, u.username, u.pass, u.activated,"
                    + " u.name, u.family, u.email, u.phone, u.adr"
                    + " FROM "
                    + " user u, role r"
                    + " where u.role = r.code "
                    + " and (activated or ?)"
                    + " and (u.role = ? or ? is null)"
                    + " and (lower(u.code) = lower(?) or ? is null)";
            if (name != null) {
                sql = sql + " and (lower(u.name) like lower('%" + name + "%')";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, all);
            ps.setString(2, role);
            ps.setString(3, role);
            ps.setString(4, code);
            ps.setString(5, code);
            if (name != null) {
                ps.setString(5, name);
                ps.setString(6, name);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User b = new User(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getBoolean(7), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getString(11));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void manageUser(User d, String action) throws SQLException {
        boolean create = false;
        String sql = "";
        if ("U".equals(action)) {
            sql = "UPDATE user SET name=?,family=?,phone=?,email=?,adr=?,updated_date=now(),updated_by=?, role=? WHERE code = ?";
        } else if ("C".equals(action)) {
            create = true;
            sql = "Insert into user (name, family, phone, email, adr, created_date, created_by, activated, role, code, username, pass) "
                    + " values(?,?,?,?,?,now(),?,1,?,?,?,?)";
        }

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, d.getName());
        stmt.setString(2, d.getFamily());
        stmt.setInt(3, d.getPhone());
        stmt.setString(4, d.getMail());
        stmt.setString(5, d.getAdr());
        stmt.setString(6, SessionUtil.getUserCode());
        stmt.setString(7, d.getRole());
        stmt.setString(8, d.getCode());

        if (create) {
            RandomString pass = new RandomString(8);
            stmt.setString(9, d.getCode());
            stmt.setString(10, pass.nextString());
        }
        stmt.executeUpdate();

    }

    public ArrayList<Role> getRole() {
        ArrayList<Role> list = new ArrayList();
        try {
            String sql = "SELECT code, name from role";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getString(1), rs.getString(2));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void resetLogInfo(String type, String val) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("Update user set " + type + " = ? where code = ?");
        stmt.setString(1, val);
        stmt.setString(2, SessionUtil.getUserCode());
        stmt.executeUpdate();
    }

    /**
     * *******************************COURSES***********************************
     */
    public ArrayList<StudentCourse> getRegisterCourses(String degree) {
        ArrayList<StudentCourse> courses = new ArrayList<StudentCourse>();
        try {
            String sql = "select code,name,credits, dc.semester"
                    + "  from course c, degreecourse dc  "
                    + " where activated "
                    + "   and c.code = dc.course "
                    + "   and dc.degree=?"
                    + "    and code in(\n"
                    + "    select cs.course from coursestudent cs\n"
                    + "     where finalgrade between 0.25 and 10 \n"
                    + "       and student = ? \n"
                    + "       and degree = ?\n"
                    + "       and mod(dc.semester,2) = ?\n"
                    + "    union \n"
                    + "	   select course \n"
                    + "      from degreecourse \n"
                    + "     where degree = ?\n"
                    + "       and mod(semester, 2) = ?\n"
                    + "       and semester = ifnull((select ifnull(max(semester)+1,1)\n"
                    + "                   	            from coursestudent\n"
                    + "  					           where student = ?"
                    + "                                                      and degree = ?),1) \n"
                    + "					)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, degree);
            ps.setString(2, SessionUtil.getUserCode());
            ps.setString(3, degree);
            ps.setString(4, SessionUtil.getSemester());
            ps.setString(5, degree);
            ps.setString(6, SessionUtil.getSemester());
            ps.setString(7, SessionUtil.getUserCode());
            ps.setString(8, degree);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(new StudentCourse(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    public void register(String course, int semester, String degree) throws SQLException {
        String sql = "Insert Into coursestudent (student, course, semester, cur_year, cur_semester, degree)"
                + " values(?,?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, SessionUtil.getUserCode());
        stmt.setString(2, course);
        stmt.setInt(3, semester);
        stmt.setString(4, SessionUtil.getYear());
        stmt.setString(5, SessionUtil.getSemester());
        stmt.setString(6, degree);
        stmt.executeUpdate();
    }

    public ArrayList<Course> getTeacherCourse() {
        ArrayList<Course> courses = new ArrayList<Course>();
        String sql = "Select code, name from course where teacher = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, SessionUtil.getUserCode());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(new Course(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    public ArrayList<StudentCourse> getStudentCourse(String course) {
        ArrayList<StudentCourse> list = new ArrayList<StudentCourse>();
        String sql = "Select student, concat(name, ' ', family), grade1, grade2, finalgrade from coursestudent C, user U"
                + "    where course = ?"
                + "      and student = u.code"
                + "      and cur_semester = ?"
                + "      and cur_year = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, course);
            ps.setString(2, SessionUtil.getSemester());
            ps.setString(3, SessionUtil.getYear());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new StudentCourse(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), course));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void modifyGrades(List<StudentCourse> list) throws SQLException {
        String sql = "Update coursestudent set grade1 = ?, grade2 = ?, finalgrade = ?"
                + " where student = ? and course = ? and cur_semester = ? and cur_year = ?";
        for (StudentCourse sc : list) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, sc.getGrade1());
            stmt.setDouble(2, sc.getGrade2());
            stmt.setDouble(3, sc.getFinalGrade());
            stmt.setString(4, sc.getStudent());
            stmt.setString(5, sc.getCourse());
            stmt.setString(6, SessionUtil.getSemester());
            stmt.setString(7, SessionUtil.getYear());
            stmt.executeUpdate();
        }
    }

    public ArrayList<StudentCourse> getStudentCourses(boolean all) {
        ArrayList<StudentCourse> list = new ArrayList<StudentCourse>();
        String sql = "select code, name, credits, cur_semester, cur_year, grade1, grade2, finalgrade \n"
                + "  from coursestudent, course \n"
                + " where code = course \n"
                + "   and student = ? \n"
                + "   and (cur_semester = ? or ? )\n"
                + "   and (cur_year = ? or ? )\n"
                + "  order by cur_year, cur_semester";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, SessionUtil.getUserCode());
            ps.setString(2, SessionUtil.getSemester());
            ps.setBoolean(3, all);
            ps.setString(4, SessionUtil.getYear());
            ps.setBoolean(5, all);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new StudentCourse(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Map<String, String> getCourses() {
        Map<String, String> courses = new LinkedHashMap<>();
        String sql = "select code, name from course where activated";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    /**
     * ***************************SCHEDULE**************************************
     */
    public void addToSchedule(Schedule s) {
        try {
            String sql = "Insert into schedule (course, day, period, semester, year, created_date, created_by) values "
                    + " (?,?,?,?,?,now(),?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s.getCourse());
            ps.setString(2, s.getDay());
            ps.setInt(3, s.getPeriod());
            ps.setString(4, SessionUtil.getSemester());
            ps.setString(5, SessionUtil.getYear());
            ps.setString(6, SessionUtil.getUserCode());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteFromSchedule(String day, String course, int period){
           try {
            String sql = "Delete from schedule where day = ? and  course = ? and period = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, day);
            ps.setString(2, course);
            ps.setInt(3, period);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    /**
     * *******************************OTHER************************************
     */
    public void delete(String table, String code) {
        try {
            PreparedStatement stmt = connection.prepareStatement("Delete from " + table + " where code = ?");
            stmt.setString(1, code);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean actdeac(String table, String code, boolean action) {
        try {
            PreparedStatement stmt = connection.prepareStatement("Update " + table + " set activated = ? where code = ?");
            stmt.setBoolean(1, action);
            stmt.setString(2, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
