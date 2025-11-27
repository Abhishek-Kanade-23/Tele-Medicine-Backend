package com.project.PatientService.DTO;

import com.project.PatientService.DTO.DOCTOR.Specialization;
import com.project.PatientService.DTO.DOCTOR.Department;
import lombok.Data;

@Data
public class DoctorDTO {
    private Long doctorId;
    private String firstName;
    private String lastName;
    private Specialization specialization;
    private Department department;
    private int experience;
    private String email;
    private String phone;
}
