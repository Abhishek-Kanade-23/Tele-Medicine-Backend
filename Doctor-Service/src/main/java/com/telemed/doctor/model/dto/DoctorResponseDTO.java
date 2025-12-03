package com.telemed.doctor.model.dto;

import com.telemed.doctor.model.enums.Specialization;
import com.telemed.doctor.model.enums.Department;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponseDTO {

    private Long doctorId;
    private String firstName;
    private String lastName;

    private Specialization specialization;
    private Department department;

    private int experience;

    private String emailId;
    private String phoneNumber;
    private String address ;
    private String gender ;
    private boolean isProfileComplete ;
}
