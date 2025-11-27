package com.project.PatientService.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {

    private Long patientId;
    private String name;
    private Integer age;
    private String gender;
    private String phone;
    private String email;
    private String address;
}
