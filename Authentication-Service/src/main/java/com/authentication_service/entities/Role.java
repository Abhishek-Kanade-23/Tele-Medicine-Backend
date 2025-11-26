package com.authentication_service.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "roles" )
public class Role {

    @Id
    private String roleId ;

    @Column( nullable = false )
    private String roleType ;

    @ManyToMany( mappedBy = "roles")
    private List<User> users = new ArrayList<>() ;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleType='" + roleType + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
