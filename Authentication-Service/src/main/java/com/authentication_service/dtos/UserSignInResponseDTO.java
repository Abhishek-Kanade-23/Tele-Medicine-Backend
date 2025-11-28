package com.authentication_service.dtos;

import java.util.List;

public class UserSignInResponseDTO {

    private Long userId ;
    private String emailId ;
    private String jwtToken ;
    private List<String> roles;
    private boolean isProfileComplete;
    private PatientDTO patientProfile;

    public boolean isProfileComplete() {
        return isProfileComplete;
    }

    public void setProfileComplete(boolean profileComplete) {
        isProfileComplete = profileComplete;
    }

    public PatientDTO getPatientProfile() {
        return patientProfile;
    }

    public void setPatientProfile(PatientDTO patientProfile) {
        this.patientProfile = patientProfile;
    }

    public UserSignInResponseDTO() {
    }

    public UserSignInResponseDTO(String emailId, String jwtToken, Long userId, List<String> roles,
                                 boolean isProfileComplete, PatientDTO patientProfile) {
        this.emailId = emailId;
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.roles = roles;
        this.isProfileComplete = isProfileComplete;
        this.patientProfile = patientProfile;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



}
