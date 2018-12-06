/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import objects.UserLogs;
import operation.SessionBean;

/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class UserLogsMangedBean {

    public UserLogsMangedBean() {
    }
    SessionBean session = new SessionBean();
    List<UserLogs> usersLogsList = new ArrayList<UserLogs>();

    public List<UserLogs> getUsersLogsList() {
        return session.getUserLogs();
    }

    public void setUsersLogsList(List<UserLogs> usersLogsList) {
        this.usersLogsList = usersLogsList;
    }



}
