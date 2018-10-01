/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import objects.Department;
import objects.User;
import operation.SessionBean;
import session.SessionUtil;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class DepartmentManagedBean {

    private String code;
    private String name;
    private String head;
    private String action;
    String codeFilter;
    private boolean readonly;
    private boolean active;
    SessionBean session = new SessionBean();

    public DepartmentManagedBean() {
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void resetValue() {
        this.code = "";
        this.name = "";
        this.action = "";
        this.head = "";
        this.readonly = false;
        this.active = true;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public String getCodeFilter() {
        return codeFilter;
    }

    public void setCodeFilter(String codeFilter) {
        this.codeFilter = codeFilter;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Map<String, String> getHeads() throws ClassNotFoundException {
        ArrayList<User> list = session.getUsers("H", false, null, null);
        Map<String, String> heads = new LinkedHashMap<>();
        list.forEach((temp) -> {
            heads.put(temp.getFullName(), temp.getCode());
        });
        return heads;
    }

    public Map<String, String> getDepartmentsList() throws ClassNotFoundException {
        ArrayList<Department> list = session.getDepartment(null);
        Map<String, String> deps = new LinkedHashMap<>();
        list.forEach((temp) -> {
            deps.put(temp.getName(), temp.getCode());
        });
        return deps;
    }

    public ArrayList<Department> getDepartments() throws ClassNotFoundException {
        return session.getDepartment(null);
    }

    public void onload() {
        resetValue();
        getDepartmentForUpdate();
    }

    private void getDepartmentForUpdate() {
        if (codeFilter != null) {
            Department d = session.getDepartment(codeFilter).get(0);
            this.code = codeFilter;
            this.head = d.getHead();
            this.name = d.getName();
            this.action = "U";
            this.codeFilter = null;
            this.readonly = true;
            this.active = d.getActive();
        } else {
            this.readonly = false;
            this.action = "C";
        }
    }

    public void save() {
        String message = "";
        Severity ms = null;
        try {
            Department d = new Department(code, name, head);
            boolean res = session.manageDepartment(d, action);
            if (res) {
                message = "Department saved successfuly";
                ms = FacesMessage.SEVERITY_INFO;
            }
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
    
    

}
