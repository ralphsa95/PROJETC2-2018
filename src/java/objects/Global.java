/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author user
 */
public class Global {
    
    private String semester;
    private String year;
    private String period;
    private String currency;
    private String opening_days;
    
    public Global (String semester, String year, String period, String currency, String opening_days ){
        this.semester = semester;
        this.year = year;
        this.period = period;
        this.currency = currency;
        this.opening_days = opening_days; 
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOpening_days() {
        return opening_days;
    }

    public void setOpening_days(String opening_days) {
        this.opening_days = opening_days;
    }
    
    
}
