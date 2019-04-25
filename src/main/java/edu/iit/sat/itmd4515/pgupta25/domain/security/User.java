/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.domain.security;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author pooja gupta
 */
@Entity
@EntityListeners(UserListener.class)
@Table(name = "sec_user")
@NamedQuery(name="User.findAll", query = "select u from User u")
public class User {

    @Id
    private String userName;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "sec_user_groups",
            joinColumns = @JoinColumn(name = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "GROUPNAME"))
    private List<Group> groups = new ArrayList<>();

    public User() {
    }

    public void addGroup(Group g){
        if(! this.groups.contains(g)){
            this.groups.add(g);
        }
        if(! g.getUsers().contains(this)){
            g.getUsers().add(this);
        }
    }
    
    public User(String userName, String password, Boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }

    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
