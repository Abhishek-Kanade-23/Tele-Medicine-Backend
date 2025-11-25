package com.tele_medicine.file_upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class FileResponse {
    private String fileName;
    private String uploadedSuccessfully;

    public FileResponse(String fileName, String uploadedSuccessfully) {
        this.fileName=fileName;
        this.uploadedSuccessfully=uploadedSuccessfully;
    }
}
