package com.authentication_service.dtos;

public class UserSignInResponseDTO {

    private String userId ;
    private String emailId ;
    private String jwtToken ;


    public UserSignInResponseDTO() {
    }

    public UserSignInResponseDTO(String emailId, String jwtToken, String userId) {
        this.emailId = emailId;
        this.jwtToken = jwtToken;
        this.userId = userId;
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
