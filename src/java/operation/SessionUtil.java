/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hp
 */
public class SessionUtil {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public static String getUserRole() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("role");
        } else {
            return null;
        }
    }
    
    public static String getName() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("name");
        } else {
            return null;
        }
    }

    public static String getUserCode() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("code");
        } else {
            return null;
        }
    }

    public static String getYear() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("year");
        } else {
            return null;
        }
    }

    public static String getSemester() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("semester");
        } else {
            return null;
        }
    }

    public static boolean isAdmin() {
        return "A".equalsIgnoreCase(SessionUtil.getUserRole());
    }

    public static boolean isTeacher() {
        return "T".equalsIgnoreCase(SessionUtil.getUserRole());
    }

    public static boolean isHead() {
        return "H".equalsIgnoreCase(SessionUtil.getUserRole());
    }

    public static boolean isStudent() {
        return "S".equalsIgnoreCase(SessionUtil.getUserRole());
    }

}
