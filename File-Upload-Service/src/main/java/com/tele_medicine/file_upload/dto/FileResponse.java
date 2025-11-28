package com.tele_medicine.file_upload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
     private Long id;
    private String fileName;
    private String fileUrl;
    private String documentType;
    private String description;
    private String recordDate;
    private String uploadedAt;
}
