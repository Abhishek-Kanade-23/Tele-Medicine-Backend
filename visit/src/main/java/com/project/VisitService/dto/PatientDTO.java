package com.project.VisitService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mirrors the response returned by Patient-Service under:
 * GET /api/patients/{id}
 */
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
