package com.authentication_service.dtos;

import org.springframework.stereotype.Component;

@Component
public class UserSignUpResponseDTO {

    private Long userId ;

    private String emailId ;

    public UserSignUpResponseDTO() {
    }

    public UserSignUpResponseDTO(Long userId, String emailId) {
        this.userId = userId;
        this.emailId = emailId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
