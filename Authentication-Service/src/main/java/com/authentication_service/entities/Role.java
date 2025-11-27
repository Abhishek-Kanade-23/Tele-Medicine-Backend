package com.authentication_service.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "roleId"
//)
@Entity
@Table( name = "roles" )
public class Role implements GrantedAuthority {

    @Id
    private Long roleId ;

    @Column( nullable = false )
    private String roleType ;

    @ManyToMany( mappedBy = "roles")
    @JsonBackReference
    private List<User> users = new ArrayList<>() ;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
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

    @Override
    public String getAuthority() {
        return this.roleType;
    }
}
