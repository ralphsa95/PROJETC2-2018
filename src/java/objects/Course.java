/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author Hp
 */
public class Course {

    private String code;
    private String name;
    private int credits;
    private boolean active;
    private String teacher;
    private String teacherName;
    private int period;
    private int day;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Course(String code, String name, int day, int period) {
        this.code = code;
        this.name = name;
        this.day = day;
        this.period = period;
    }

    public Course(String code, String name, int credits, String teacher, String teacherName) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.active = true;
        this.teacher = teacher;
        this.teacherName = teacherName;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    
    
}
