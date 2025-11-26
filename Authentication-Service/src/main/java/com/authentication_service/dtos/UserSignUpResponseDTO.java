package com.authentication_service.dtos;

import org.springframework.stereotype.Component;

@Component
public class UserSignUpResponseDTO {

    private String userId ;

    private String emailId ;

    public UserSignUpResponseDTO() {
    }

    public UserSignUpResponseDTO(String userId, String emailId) {
        this.userId = userId;
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "UserSignUpResponseDTO{" +
                "userId='" + userId + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
