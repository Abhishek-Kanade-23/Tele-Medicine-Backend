package com.authentication_service.dtos;

public class UserSignInResponseDTO {
    private PatientProfileDTO patientProfile ;
    private DoctorProfileDTO doctorProfile ;
    private String jwtToken;


    public UserSignInResponseDTO() {
    }

    public UserSignInResponseDTO(PatientProfileDTO patientProfile, DoctorProfileDTO doctorProfile, String jwtToken, boolean isProfileComplete) {
        this.patientProfile = patientProfile;
        this.doctorProfile = doctorProfile;
        this.jwtToken = jwtToken;
        
    }

    public PatientProfileDTO getPatientProfile() {
        return patientProfile;
    }

    public void setPatientProfile(PatientProfileDTO patientProfile) {
        this.patientProfile = patientProfile;
    }

    public DoctorProfileDTO getDoctorProfile() {
        return doctorProfile;
    }

    public void setDoctorProfile(DoctorProfileDTO doctorProfile) {
        this.doctorProfile = doctorProfile;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

   

    @Override
    public String toString() {
        return "UserSignInResponseDTO{" +
                "patientProfile=" + patientProfile +
                ", doctorProfile=" + doctorProfile +
                ", jwtToken='" + jwtToken + '\'' +
                
                '}';
    }
}
