/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import operation.SessionBean;
import objects.*;
import session.SessionUtil;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class ManageScheduleBean {

    private String course;
    private String day;
    private int period;
    private Map<String, String> daysMap = new LinkedHashMap<>();
    private String daysList[];
    private String message;
    private String styleClass;

    private ArrayList<ArrayList<ArrayList<Course>>> all;

    SessionBean session = new SessionBean();

    public ManageScheduleBean() {
    }

    public void onload() {
        message = "";
        styleClass = "";
        course = "";
        String days = "Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday";
        daysList = days.split(",");
        search();
    }

    public Map<String, String> getDaysMap() throws ClassNotFoundException {
        for (int i = 0; i < daysList.length; i++) {
            daysMap.put(daysList[i], String.valueOf(i));
        }
        return daysMap;
    }

    public ArrayList<Integer> getPeriods() throws ClassNotFoundException {
        ArrayList<Integer> periods = new ArrayList<Integer>();
        periods.add(1);
        periods.add(2);
        periods.add(3);
        return periods;
    }

    public Map<String, String> getCourses() {
        return session.getCourses();
    }

    public void add() {
        try {
            session.addToSchedule(new Schedule(getCourse(), getDay(), getPeriod()));
            search();
            styleClass = "success";
            message = "Class added with success";

        } catch (SQLException ex) {
            Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
            styleClass = "error";
            message = "Error: " + ex;
        }
    }

    public void delete(String course, int day, int period) {
        try {
            session.deleteFromSchedule(day, course, period);
            search();
            styleClass = "success";
            message = "Class deleted with success";
        } catch (SQLException ex) {
            Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
            styleClass = "error";
            message = "Error: " + ex;
        }

    }

    public void search() {
        setAll(null);
        int xday = -1;
        int xperiod = -1;
        ArrayList<Course> allSchedule = session.getScheduleCourses(course);
        ArrayList<Course> courses = new ArrayList<Course>();
        Course c;
        if (allSchedule != null) {
            for (int i = 0; i < allSchedule.size(); i++) {
                c = allSchedule.get(i);
                if (xday == -1) {
                    xday = c.getDay();
                }
                if (xperiod == -1) {
                    xperiod = c.getPeriod();
                }
                if (xday != c.getDay() || xperiod != c.getPeriod()) {
                    courses = new ArrayList<Course>();
                }
                courses.add(c);
                all.get(c.getPeriod()).set(c.getDay() + 1, courses);
                xday = c.getDay();
            }
        }
    }

    /**
     * Getters and Setters*
     */
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public ArrayList<ArrayList<ArrayList<Course>>> getAll() {
        return all;
    }

    public void setAll(ArrayList<ArrayList<ArrayList<Course>>> allx) {
        int totalPeriods = SessionUtil.getPeriod(); //tochange
        int totalDays = SessionUtil.getOpeningDays() + 1; //tochange 5
        all = new ArrayList<ArrayList<ArrayList<Course>>>();
        for (int i = 0; i < totalPeriods; i++) {
            all.add(new ArrayList<ArrayList<Course>>());
            ArrayList<ArrayList<Course>> period = new ArrayList<ArrayList<Course>>();
            period = all.get(i);
            for (int j = 0; j < totalDays; j++) {
                period.add(new ArrayList<Course>());
            }
        }
    }

    public String[] getDaysList() {
        String[] out = new String[SessionUtil.getOpeningDays()];
        for (int i = 0; i < SessionUtil.getOpeningDays(); i++) {
            out[i] = daysList[i];
        }
        return out;
    }

    public void setDaysList(String[] daysList) {
        this.daysList = daysList;
    }

    public int getColumnsNumber() {
        return getDaysList().length + 1;
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
