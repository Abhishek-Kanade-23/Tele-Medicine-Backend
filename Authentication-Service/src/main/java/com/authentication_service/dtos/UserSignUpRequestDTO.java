package com.authentication_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSignUpRequestDTO {

    @Email(message = "Email Id Should Be In Proper Format")
    private String emailId ;

    @NotBlank(message = "Password Cannot Be Blank")
    private String password ;

    @NotNull(message = "User Roles Cannot Be Blank")
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
