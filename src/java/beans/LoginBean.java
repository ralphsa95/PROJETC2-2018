/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import objects.Login;
import org.apache.catalina.manager.util.SessionUtils;
import session.SessionUtil;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    private String pwd;
    private String msg;
    private String user;
    private String name;

    public LoginBean() {
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String validateUsernamePassword() {
        boolean valid = Login.validate(user, pwd);
        if (valid) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            String contextPath = origRequest.getContextPath();
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(contextPath + "/faces/home.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "home";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username or Passowrd",
                            "Please enter correct username and Password"));
            return "login";
        }
    }

    public String getName() {
        HttpSession session = SessionUtil.getSession();
        return (String) session.getAttribute("name");
    }

    public String logout() {
        HttpSession session = SessionUtil.getSession();
        session.invalidate();
        return "login";
    }
    
    public boolean isAdmin(){
        return "A".equalsIgnoreCase(SessionUtil.getUserRole());
    }
    
    public boolean isTeacher(){
        return "T".equalsIgnoreCase(SessionUtil.getUserRole());
    }
        
    public boolean isHead(){
        return "H".equalsIgnoreCase(SessionUtil.getUserRole());
    }
    
    public boolean isStudent(){
        return "S".equalsIgnoreCase(SessionUtil.getUserRole());
    }

}
