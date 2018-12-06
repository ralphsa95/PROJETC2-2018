/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import objects.*;
import operation.SessionBean;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class RegsitrationBean {

    private String degree;
    private List<StudentCourse> courses = new ArrayList<StudentCourse>();
    private Map<String, Boolean> checkedCourse = new HashMap<String, Boolean>();

    SessionBean session = new SessionBean();

    public RegsitrationBean() {
    }
    private String message;
    private String styleClass;

    public void save() {
        boolean ok = true;
        for (StudentCourse sc : courses) {
            if (checkedCourse.get(sc.getCourse())) {
                try {
                    session.register(sc.getCourse(), sc.getSemester(), degree);
                } catch (SQLException ex) {
                    ok = false;
                    styleClass = "error";
                    message = "Error: " + ex.toString();
                }
            }
        }
        if (ok) {
            styleClass = "success";
            message = "Registered with success";
        }

    }

    public void search() {
        courses = session.getRegisterCourses(degree);
    }

    /**
     * Getters and setters*
     */
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<StudentCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<StudentCourse> courses) {
        this.courses = courses;
    }

    public Map<String, Boolean> getCheckedCourse() {
        return checkedCourse;
    }

    public void setCheckedCourse(Map<String, Boolean> checkedCourse) {
        this.checkedCourse = checkedCourse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

}
