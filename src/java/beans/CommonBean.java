/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private String message;
    private String styleClass;



    public void deleteCommon(String table, String code) throws IOException {
        session.delete(table, code);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/search.xhtml?list=" + table);
    }

    public void actdeactCommon(String table, String code, Boolean action) throws IOException {
        session.actdeac(table, code, action);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/search.xhtml?list=" + table);
    }

    public void dbBackup() {
        try {
            String executeCmd = "mysqldump -u root -padmin uni -r backup.sql";
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
        } catch (Exception ex) {
            Logger.getLogger(CommonBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void handleDBExecutions(Exception ex, String message) {
        if (ex != null) {
            setStyleClass("error");
            setMessage("Error: " + ex);
        } else {
            setStyleClass("success");
            setMessage(message);
        }
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
