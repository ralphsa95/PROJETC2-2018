/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import objects.Degree;
import objects.Department;
import operation.SessionBean;

/**
 *
 * @author Hp
 */
@ManagedBean
@ViewScoped
public class DegreeManagedBean {

    private String code;
    private String name;
    private int credits;
    private double price;
    private String department;
    private boolean active;
    private String codeFilter;
    private String action;
    private boolean readonly = false;
    String depFilter;
    SessionBean session = new SessionBean();

    public void onload() {
        if (codeFilter != null) {
            Degree d = session.getDegree(codeFilter, null).get(0);
            this.code = d.getCode();
            this.name = d.getName();

            codeFilter = null;
        }
    }

    public void save() {
    }

    public DegreeManagedBean() {
        depFilter = null;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCodeFilter() {
        return codeFilter;
    }

    public void setCodeFilter(String codeFilter) {
        this.codeFilter = codeFilter;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public ArrayList<Degree> getDegrees() {
        return session.getDegree(null, depFilter);
    }

    public String getDepFilter() {
        return depFilter;
    }

    public void setDepFilter(String depFilter) {
        this.depFilter = depFilter;
    }

    public Map<String, String> getDegreesList() throws ClassNotFoundException {
        ArrayList<Degree> list = session.getDegree(null, null);
        Map<String, String> degs = new LinkedHashMap<>();
        list.forEach((temp) -> {
            degs.put(temp.getName(), temp.getCode());
        });
        return degs;
    }
}
