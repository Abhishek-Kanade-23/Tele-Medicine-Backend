package com.tele_medicine.file_upload.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorFileResponse {
    private Long id;
    private String fileName;
    private String documentType;
    private String description;
    private String recordDate;
    private String uploadedAt;
    private String patientId; 
}
