package com.authentication_service.dtos;

public class DoctorProfileDTO {

    private Long doctorId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String department;
    private int experience;
    private String emailId;
    private String phoneNumber;
    private String gender ;
    private boolean isProfileComplete ;
    private String address ;

    public DoctorProfileDTO() { }

    public DoctorProfileDTO(Long doctorId, String firstName, String lastName, String specialization, String department, int experience, String emailId, String phoneNumber, String gender , boolean isProfileComplete , String address) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.department = department;
        this.experience = experience;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.isProfileComplete = isProfileComplete;
        this.address = address ;
    }

     public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isProfileComplete() {
        return isProfileComplete;
    }

    public void setProfileComplete(boolean profileComplete) {
        isProfileComplete = profileComplete;
    }



    @Override
    public String toString() {
        return "DoctorProfileDTO{" +
                "doctorId=" + doctorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", department='" + department + '\'' +
                ", experience=" + experience +
                ", emailId='" + emailId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", isProfileComplete=" + isProfileComplete +
                '}';
    }

   
}
