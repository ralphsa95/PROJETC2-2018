/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.Date;

/**
 *
 * @author user
 */
public class UserLogs {

    private String user;
    private String login_date;
    private String logout_date;
    private int id;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLogin_date() {
        return login_date;
    }

    public void setLogin_date(String login_date) {
        this.login_date = login_date;
    }

    public String getLogout_date() {
        return logout_date;
    }

    public void setLogout_date(String logout_date) {
        this.logout_date = logout_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserLogs(String user, String login_date, String logout_date, int id) {
        this.user = user;
        this.login_date = login_date;
        this.logout_date = logout_date;
        this.id = id;
    }

}
