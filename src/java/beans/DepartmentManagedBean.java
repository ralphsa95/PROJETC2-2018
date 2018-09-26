/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import javax.annotation.PostConstruct;
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

    public Map<String, String> getHeads() throws ClassNotFoundException {
        ArrayList<User> list = session.getUsers("H", false);
        Map<String, String> heads = new LinkedHashMap<>();
        list.forEach((temp) -> {
            heads.put(temp.getName(), temp.getCode());
        });
        return heads;
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
        } else {
            this.action = "C";
        }
    }

    public void save() {
        Department d = new Department(code, name, head);
        boolean res = session.manageDepartment(d, action);
    }

}
