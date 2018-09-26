/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author Hp
 */
public class Department {
    private String code;
    private String name;
    private String head;
    private String headName;
    private Boolean active;


    
    public Department(String code, String name, String head) {
        this.code = code;
        this.name = name;
        this.head = head;
    }
    
    public Department(String code, String name, String headname, boolean active) {
        this.code = code;
        this.name = name;
        this.headName = headname;
        this.active = active;
    }

    public Department(String code, String name, String head, String headName, Boolean active) {
        this.code = code;
        this.name = name;
        this.head = head;
        this.headName = headName;
        this.active = active;
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

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    
}
