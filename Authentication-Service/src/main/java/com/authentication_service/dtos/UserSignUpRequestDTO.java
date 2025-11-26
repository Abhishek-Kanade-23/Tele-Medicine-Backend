package com.authentication_service.dtos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSignUpRequestDTO {

    private String emailId ;

    private String password ;

    private List<String> roles ;

    public UserSignUpRequestDTO() {
    }

    public UserSignUpRequestDTO(String emailId, String password, List<String> roles) {
        this.emailId = emailId;
        this.password = password;
        this.roles = roles;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "UserSignUpRequestDTO{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
