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
public class StudentCourse {

    private String course;
    private String student;
    private String degree;
    private String courseName;
    private String studentName;
    private int credits;
    private int year;
    private int semester;
    private int cur_year;
    private int cur_semester;
    private double grade1;
    private double grade2;
    private double finalGrade;

    public StudentCourse(String course, String courseName, int credits, int semester) {
        this.course = course;
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
    }

    public StudentCourse(String student, String studentName, double grade1, double grade2, double finalgrade,String course){
       this.student = student;
       this.studentName = studentName;
       this.grade1 = grade1;
       this.grade2 = grade2;
       this.finalGrade = finalgrade;
       this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getCur_year() {
        return cur_year;
    }

    public void setCur_year(int cur_year) {
        this.cur_year = cur_year;
    }

    public int getCur_semester() {
        return cur_semester;
    }

    public void setCur_semester(int cur_semester) {
        this.cur_semester = cur_semester;
    }

    public double getGrade1() {
        return grade1;
    }

    public void setGrade1(double grade1) {
        this.grade1 = grade1;
    }

    public double getGrade2() {
        return grade2;
    }

    public void setGrade2(double grade2) {
        this.grade2 = grade2;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

}
