/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mysql.jdbc.StringUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import objects.Department;
import objects.Role;
import objects.User;
import operation.RandomString;
import operation.SessionBean;
import session.SessionUtil;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class UserManagedBean {

    private String role;
    private String roleName;
    private String code;
    private String name;
    private String family;
    private int phone;
    private String mail;
    private String adr;
    private boolean active;
    private String old;
    private String new1;
    private String new2;
    private String action;
    private String codeFilter;
    private String roleFilter;
    private String nameFilter;
    private boolean readOnly;

    private String type;

    SessionBean session = new SessionBean();

    public UserManagedBean() {
    }

    List<User> userslist = new ArrayList<User>();

    public void save() {
        String message = "";
        FacesMessage.Severity ms = null;
        try {
            session.manageUser(new User(code, role, null, null, null, true, name, family, mail, phone, adr), action);

            message = "User saved successfuly";
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

    public Map<String, String> getRoles() {
        ArrayList<Role> list = session.getRole();
        Map<String, String> roles = new LinkedHashMap<>();
        list.forEach((temp) -> {
            roles.put(temp.getName(), temp.getCode());
        });
        return roles;
    }

    public void search() {
        if (StringUtils.isEmptyOrWhitespaceOnly(codeFilter)) {
            codeFilter = null;
        }
        if (StringUtils.isEmptyOrWhitespaceOnly(nameFilter)) {
            nameFilter = null;
        }
        userslist = session.getUsers(roleFilter, true, codeFilter, nameFilter);
    }

    public List<User> getUserslist() {
        return userslist;
    }

    public void setUserslist(List<User> userslist) {
        this.userslist = userslist;
    }

    public void onload() {
        if (codeFilter != null) {
            User d = session.getUsers(null, true, codeFilter, null).get(0);
            this.code = codeFilter;
            this.role = d.getRole();
            this.name = d.getName();
            this.family = d.getFamily();
            this.mail = d.getMail();
            this.phone = d.getPhone();
            this.adr = d.getAdr();
            this.action = "U";
            this.codeFilter = null;
            this.readOnly = true;
            this.active = d.isActive();
            this.roleName = d.getRoleName();
        } else {
            this.readOnly = false;
            this.action = "C";
            RandomString us = new RandomString(6);
            this.code = us.nextString();
            this.role = "";
            this.name = "";
            this.family = "";
            this.mail = "";
            this.phone = 03;
            this.adr = "";
            this.action = "C";
            this.codeFilter = null;
            this.active = true;
            this.roleName = "";
        }
    }

    /**
     * Getters and Setters
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getNew1() {
        return new1;
    }

    public void setNew1(String new1) {
        this.new1 = new1;
    }

    public String getNew2() {
        return new2;
    }

    public void setNew2(String new2) {
        this.new2 = new2;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCodeFilter() {
        return codeFilter;
    }

    public void setCodeFilter(String codeFilter) {
        this.codeFilter = codeFilter;
    }

    public String getRoleFilter() {
        return roleFilter;
    }

    public void setRoleFilter(String roleFilter) {
        this.roleFilter = roleFilter;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    
    
    public String resetLogInfo() {
        User currentUser = session.getUsers(null, true, SessionUtil.getUserCode(), null).get(0);
        String current = type.equals("username") ? currentUser.getUsername() : currentUser.getPassword();
        if (!current.equals(old)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Enetered " + type + " different from current",
                            "Enetered " + type + " different from current"));
        } else if (!new1.equals(new2)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Rentered " + type + " is wrong",
                            "Rentered " + type + " is wrong"));
        } else {
            try {
                session.resetLogInfo(type, new1);
                HttpSession session = SessionUtil.getSession();
                session.invalidate();
                return "login";
            } catch (SQLException ex) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error " + ex.toString(),
                                "Error " + ex.toString()));
            }
        }
        return null;
    }
}
