/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mysql.jdbc.StringUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import objects.*;
import operation.SessionBean;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class courseManagedBean {

    /**
     * Creates a new instance of courseManagedBean
     */
    public courseManagedBean() {
        
    }
    private String code;
    private String name;
    private int credits;
    private boolean active;
    private String teacher;
    private int period;
    private int day;
    private String codeFilter;
    private String nameFilter;
    private String action;
    private boolean readonly = false;
    List<Course> coursesList = new ArrayList<Course>();

    SessionBean session = new SessionBean();

    private String styleClass;
    private String message;

    public void onload() {
        styleClass = "";
        message = "";
        action = "C";
        readonly = false;
        this.code = "";
        this.name = "";
        this.credits = 0;
        this.teacher = "";
        if (codeFilter != null) {
            Course c = session.getCourse(codeFilter, null).get(0);
            this.code = c.getCode();
            this.name = c.getName();
            this.credits = c.getCredits();
            this.teacher = c.getTeacher();
            action = "U";
            readonly = true;
            codeFilter = null;
        }
        search();
    }

    public void save() {
        try {
            Course d = new Course(code, name, credits, teacher, null);
            session.manageCourse(d, action);
            message = "Course saved successfuly";
            styleClass = "success";
        } catch (SQLException ex) {
            message = "Error: " + ex.toString();
            styleClass = "error";
            Logger.getLogger(DepartmentManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search() {
        if (StringUtils.isEmptyOrWhitespaceOnly(codeFilter)) {
            codeFilter = null;
        }
        if (StringUtils.isEmptyOrWhitespaceOnly(nameFilter)) {
            nameFilter = null;
        }
        coursesList = session.getCourse(codeFilter, nameFilter);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public String getCodeFilter() {
        return codeFilter;
    }

    public void setCodeFilter(String codeFilter) {
        this.codeFilter = codeFilter;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public Map<String, String> getTeachers() throws ClassNotFoundException {
        ArrayList<User> list = session.getUsers("T", false, null, null);
        Map<String, String> teachers = new LinkedHashMap<>();
        list.forEach((temp) -> {
            teachers.put(temp.getName(), temp.getCode());
        });
        return teachers;
    }
}
