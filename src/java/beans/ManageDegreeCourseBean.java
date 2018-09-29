/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import objects.DegreeCourse;
import objects.Course;
import operation.SessionBean;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class ManageDegreeCourseBean{

    private String degreeFilter;
    private int semester;
    private String course;
    
    SessionBean session = new SessionBean();

    public ManageDegreeCourseBean() {
    }

    public String getDegreeFilter() {
        return degreeFilter;
    }

    public void setDegreeFilter(String degreeFilter) {
        this.degreeFilter = degreeFilter;
    } 
    
    public ArrayList<DegreeCourse> getDegreeCourses() {
        return session.getDegreeCourse(degreeFilter);
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    
    public Map<String, String> getCourses() throws ClassNotFoundException {
        ArrayList<Course> list = session.getCoursesForDegee(degreeFilter);
        Map<String, String> courses = new LinkedHashMap<>();
        list.forEach((temp) -> {
            courses.put(temp.getName(), temp.getCode());
        });
        return courses;
    }
    
    public void add(){
      DegreeCourse dc= new DegreeCourse(degreeFilter, course, semester, 0, null, null);
      session.manageDegreeCourse(dc, "C");
    }
    
    public void delete(String c){
      DegreeCourse dc= new DegreeCourse(degreeFilter, c, 0, 0, null, null);
      session.manageDegreeCourse(dc, "D");
    }

    
    




}
