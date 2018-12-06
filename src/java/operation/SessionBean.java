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
import java.sql.ResultSetMetaData;
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
            String sql = "SELECT d.code, d.name name, de.code department ,de.name departmentName, d.price,"
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
                        rs.getString(4), rs.getDouble(5), rs.getBoolean(6));
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void manageDegree(Degree d, String action) throws SQLException {
        String sql = "";
        if ("U".equals(action)) {
            sql = "UPDATE degree SET name=?,price=?,department=?,updated_date=now(),updated_by=? WHERE code = ?";
        } else if ("C".equals(action)) {
            sql = "Insert into degree (name, price, department, created_date, created_by, activated, code) "
                    + " values(?,?,?,now(),?,1,?)";
        }

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, d.getName());
        stmt.setDouble(2, d.getPrice());
        stmt.setString(3, d.getDepartment());
        stmt.setString(4, SessionUtil.getUserCode());
        stmt.setString(5, d.getCode());

        stmt.executeUpdate();

    }

    /**
     * ****************************DegreesCourse********************************
     */
    public ArrayList<DegreeCourse> getDegreeCourse(String degree) {
        ArrayList<DegreeCourse> list = new ArrayList();
        try {
            String sql = "Select dc.degree, dc.course, dc.semester, c.credits, d.name, c.name"
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
            String sql = "Select c.code, c.name"
                    + "     From course c"
                    + "    where activated"
                    + "      and c.code not in(select course from degreecourse where degree = ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, degree);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course(rs.getString(1), rs.getString(2), 0, null, null);
                list.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void manageDegreeCourse(DegreeCourse dc, String action) throws SQLException {
        String sql = "";
        boolean create = false;
        if ("D".equalsIgnoreCase(action)) {
            sql = "Delete from degreecourse where degree = ? and course = ?";
        } else {
            create = true;
            sql = "Insert into degreecourse (degree, course, semester, created_date, created_by) values "
                    + "(?, ?, ?, current_date, ?)";
        }
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, dc.getDegree());
        ps.setString(2, dc.getCourse());
        if (create) {
            ps.setInt(3, dc.getSemester());
            ps.setString(4, SessionUtil.getUserCode());
        }

        ps.executeUpdate();

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
                sql = sql + " and lower(u.name) like lower('%" + name + "%')";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, all);
            ps.setString(2, role);
            ps.setString(3, role);
            ps.setString(4, code);
            ps.setString(5, code);
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
    public ArrayList<Course> getCourse(String code, String name) {
        ArrayList<Course> list = new ArrayList();
        try {
            String sql = "Select c.code, c.name, c.credits, c.teacher, u.name"
                    + "     From course c LEFT JOIN user u"
                    + "       ON c.teacher = u.code"
                    + "    Where c.code = ? or ? is null";
            if (name != null) {
                sql = sql + " and lower(c.name) like lower('%" + name + "%')";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ps.setString(2, code);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                list.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

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
        String sql = "Select student, concat(name, ' ', family), grade1, grade2, finalgrade from coursestudent c, user u"
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
                courses.put(rs.getString(2), rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    public void manageCourse(Course c, String action) throws SQLException {
        String sql = "";
        if ("U".equals(action)) {
            sql = "UPDATE course SET name=?,credits=?,teacher=?,updated_date=now(),updated_by=? WHERE code = ?";
        } else {
            sql = "Insert into course(name, credits, teacher, created_date, created_by, activated, code) "
                    + " values(?,?,?,now(),?,1,?)";
        }

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, c.getName());
        stmt.setInt(2, c.getCredits());
        stmt.setString(3, c.getTeacher());
        stmt.setString(4, SessionUtil.getUserCode());
        stmt.setString(5, c.getCode());

        stmt.executeUpdate();

    }

    /**
     * ***************************SCHEDULE**************************************
     */
    public ArrayList<Course> getScheduleCourses(String course) {
        ArrayList<Course> courses = new ArrayList<Course>();
        String sql = "";
        if (SessionUtil.isStudent()) {
            sql = "select c.code, c.name, s.day, s.period from course c, coursestudent cs, schedule s where "
                    + " c.code = cs.course and s.course = cs.course and cur_year = ? and cur_semester = ? and cs.student = ? order by s.period, s.day";
        } else if (SessionUtil.isTeacher()) {
            sql = "select c.code, c.name, s.day, s.period from course c, schedule s"
                    + " where s.course = code and c.teacher = ? order by s.period, s.day";
        } else if (SessionUtil.isAdmin()) {
            sql = "select c.code, c.name, s.day, s.period from course c, schedule s"
                    + " where s.course = code and c.code = ? order by s.period, s.day";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (SessionUtil.isAdmin()) {
                ps.setString(1, course);
            } else {
                ps.setString(1, SessionUtil.getUserCode());
                if (SessionUtil.isStudent()) {
                    ps.setString(2, SessionUtil.getYear());
                    ps.setString(3, SessionUtil.getSemester());
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(new Course(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    public void addToSchedule(Schedule s) throws SQLException {

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

    }

    public void deleteFromSchedule(int day, String course, int period) throws SQLException {
        String sql = "Delete from schedule where day = ? and  course = ? and period = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, day);
        ps.setString(2, course);
        ps.setInt(3, period);
        ps.executeUpdate();

    }

    /**
     *******************************USER
     * LOGS************************************
     */
    public int getLastUserId(String user) {
        int res = 0;
        try {
            String sql = "SELECT MAX(id) FROM user_logs WHERE user = ? ";
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setString(1, user);
            ResultSet result = stmnt.executeQuery();
            if (result.next()) {
                res = result.getInt(1);
            } else {
                res = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;

    }

    public void insertUserLog(String user, String action) {
        try {
            String sql = "";
            int id = getLastUserId(user);
            if ("U".equals(action)) {
                sql = "UPDATE user_logs SET logout_date = current_date WHERE user=? AND id=?";
            } else if ("I".equals(action)) {
                id++;
                sql = "INSERT INTO user_logs (user, login_date, id) VALUES (?,current_date,?)";
            }
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setString(1, user);
            stmnt.setInt(2, id);
            stmnt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<UserLogs> getUserLogs() {
        ArrayList<UserLogs> list = new ArrayList<UserLogs>();
        try {
            String sql = "SELECT user, login_date, logout_date, id FROM user_logs order by ifnull(logout_date, login_date)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new UserLogs(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     *******************************OTHER************************************
     *
     */
    public Global getGlobal() throws SQLException {
        String sql = "SELECT curr_semester, curr_year, period, currency, opening_days FROM global";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Global global = null;
        while (rs.next()) {
            global = new Global(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
        }
        return global;
    }

    public void manageGlobal(String curr_semester, String year, String period, String currency, String opening_days) throws SQLException {
        String sql = "UPDATE global SET curr_semester=?, curr_year=?, period=?, currency=?, opening_days=?, updated_by =?, updated_date = current_date";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, curr_semester);
        ps.setString(2, year);
        ps.setString(3, period);
        ps.setString(4, currency);
        ps.setString(5, opening_days);
        ps.setString(6, SessionUtil.getUserCode());

        ps.executeUpdate();
    }

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

    /**
     * *******************************HISTORY************************************
     */
    public Map<String, String> getTables() {

        Map<String, String> tables = new LinkedHashMap();
        try {
            String sql = "SELECT table_name FROM information_schema.tables where UPPER(table_schema) ='UNI' and UPPER(table_name) not like '%_HIST' "
                    + "   and table_name in (select substr(table_name,1, length(table_name) -5)"
                    + "                        from information_schema.tables "
                    + "                       where UPPER(table_schema) ='UNI' "
                    + "                         and UPPER(table_name) like '%_HIST') order by table_name";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tables.put(rs.getString(1), rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tables;
    }

    public ArrayList<String> getColumns(String table) {
        table = table + "_hist";
        ArrayList<String> columns = new ArrayList<String>();
        try {
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE UPPER(TABLE_SCHEMA) = 'UNI'"
                    + "  AND UPPER(TABLE_NAME) = UPPER(?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, table);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                columns.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columns;
    }

    public ArrayList<ArrayList<String>> getHistory(String table) {
        ArrayList<ArrayList<String>> history = new ArrayList<ArrayList<String>>();
        try {
            String sql = "SELECT * FROM " + table + "_hist  order by ifnull(updated_date,created_date) desc, created_date desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colsNumber = rsmd.getColumnCount();
            //
            while (rs.next()) {
                ArrayList<String> columns = new ArrayList<String>();
                for (int i = 1; i <= colsNumber; i++) {
                    columns.add(rs.getString(i));
                }
                history.add(columns);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return history;
    }
}
