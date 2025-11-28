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
    private String firstName ;
    private String lastName ;
    private Integer age;
    private String gender;
    private String phone;
    private String emailId;
    private String address;
    private float weight ;
    private String bloodGroup ;
}
