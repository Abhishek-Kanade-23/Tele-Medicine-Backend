package com.authentication_service.dtos;

import java.util.List;

public class UserSignInResponseDTO {

    private String userId ;
    private String emailId ;
    private String jwtToken ;
    private List<String> roles;


    public UserSignInResponseDTO() {
    }

    public UserSignInResponseDTO(String emailId, String jwtToken, String userId,List<String>roles) {
        this.emailId = emailId;
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.roles=roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



}
