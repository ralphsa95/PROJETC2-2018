/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import objects.*;
import operation.SessionBean;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class manageGlobalBean {

    /**
     * Creates a new instance of manageGlobalBean
     */
    private String semester;
    private String year;
    private String period;
    private String currency;
    private String opening_days;
    private String styleClass;
    private String message;

    SessionBean session = new SessionBean();

    public manageGlobalBean() {

    }

    public void onload() throws SQLException {
        Global global = session.getGlobal();
        semester = global.getSemester();
        year = global.getYear();
        period = global.getPeriod();
        currency = global.getCurrency();
        opening_days = global.getOpening_days();
        styleClass = "";
        message = "";
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

    public void save() {
        try {
            session.manageGlobal(semester, year, period, currency, opening_days);
            styleClass = "success";
            message = "Global updated successfully";
        } catch (SQLException ex) {
            styleClass = "error";
            message = "Error: " + ex.toString();
            Logger.getLogger(manageGlobalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
