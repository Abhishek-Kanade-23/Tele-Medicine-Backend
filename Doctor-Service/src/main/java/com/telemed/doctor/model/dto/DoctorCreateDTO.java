package com.telemed.doctor.model.dto;

import com.telemed.doctor.model.enums.Specialization;
import com.telemed.doctor.model.enums.Department;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorCreateDTO {

    private Long doctorId;
    private String firstName;
    private String lastName;

    private Specialization specialization;
    private Department department;

    private int experience;

    private String emailId;
    private String phone;
    private String gender ;
    private String address ;
}
