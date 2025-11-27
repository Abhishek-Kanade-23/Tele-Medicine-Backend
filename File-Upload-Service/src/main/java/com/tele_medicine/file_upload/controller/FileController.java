package com.tele_medicine.file_upload.controller;

import com.tele_medicine.file_upload.dto.FileResponse;
import com.tele_medicine.file_upload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @PostMapping(value="/upload",consumes="multipart/form-data")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {

        // Validate file
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "File is empty. Please upload a valid file.",
                    "status", "400"
            ));
        }

        // Validate file type
        List<String> allowedTypes = Arrays.asList(
                "image/jpeg",
                "image/png",
                "application/pdf"
        );

        if (file.getContentType() == null || !allowedTypes.contains(file.getContentType())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Invalid file type. Allowed: JPG, PNG, PDF.",
                    "status", "400"
            ));
        }

        // Upload + generate presigned URL
        FileResponse response = fileService.uploadFileAndGenerateUrl(file);

        return ResponseEntity.ok(response);
    }
}
