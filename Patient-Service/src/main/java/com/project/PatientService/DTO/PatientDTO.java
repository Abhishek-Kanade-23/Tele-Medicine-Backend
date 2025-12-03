package com.project.PatientService.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {

    private Long patientId;
    private String firstName ;
    private String lastName ;
    private Integer age;
    private String gender;
    private String phone;
    private String emailId;
    private String address;
    private float weight ;
    private String bloodGroup ;
    private boolean isProfileComplete ;
}
