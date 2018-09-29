/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import operation.SessionBean;
import session.SessionUtil;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class CommonBean {

    SessionBean session = new SessionBean();

    public void delete(String table, String code) throws IOException {
        session.delete(table, code);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/search.xhtml?list=" + table);
    }

    public void actdeact(String table, String code, Boolean action) throws IOException {
        session.actdeac(table, code, action);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/search.xhtml?list=" + table);
    }

    public boolean isAdmin() {
        return SessionUtil.isAdmin();
    }

    public boolean isHead() {
        return SessionUtil.isHead();
    }

    public boolean isTeacher() {
        return SessionUtil.isTeacher();
    }

    public boolean isStudent() {
        return SessionUtil.isStudent();
    }
}
