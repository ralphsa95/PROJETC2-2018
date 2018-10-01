/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import objects.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import operation.SessionBean;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class StudentCourseBean {

    SessionBean session = new SessionBean();
    private String course;
    private List<StudentCourse> studentsgrades = new ArrayList<StudentCourse>();

    public StudentCourseBean() {
    }

    public Map<String, String> getTeacherCourse() {
        ArrayList<Course> list = session.getTeacherCourse();
        Map<String, String> courses = new LinkedHashMap<>();
        list.forEach((temp) -> {
            courses.put(temp.getName(), temp.getCode());
        });
        return courses;
    }

    public void search() {
        studentsgrades = session.getStudentCourse(course);
    }

    public void save() {
        String message = "";
        FacesMessage.Severity ms = null;
        try {
            session.modifyGrades(studentsgrades);
            message = "Grades saved successfuly";
            ms = FacesMessage.SEVERITY_INFO;

        } catch (SQLException ex) {
            message = "Error: " + ex.toString();
            ms = FacesMessage.SEVERITY_ERROR;
            //Logger.getLogger(DepartmentManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(ms,
                        message,
                        message));
    }

    public ArrayList<StudentCourse> getStudentCourses(String all){
      return session.getStudentCourses(Boolean.parseBoolean(all));
    }
    /**
     * Getters and setters*
     */
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<StudentCourse> getStudentsgrades() {
        return studentsgrades;
    }

    public void setStudentsgrades(List<StudentCourse> studentsgrades) {
        this.studentsgrades = studentsgrades;
    }
}
