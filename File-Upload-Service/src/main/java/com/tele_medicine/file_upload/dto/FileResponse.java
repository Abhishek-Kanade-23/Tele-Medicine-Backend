package com.tele_medicine.file_upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String fileName;
    private String message;
    private String presignedUrl;
}
