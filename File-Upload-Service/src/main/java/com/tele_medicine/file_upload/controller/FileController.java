package com.tele_medicine.file_upload.controller;

import com.tele_medicine.file_upload.dto.FileResponse;
import com.tele_medicine.file_upload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

   @Autowired
    private  FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {

//        If file is empty or not added
        if (file == null || file.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "File is empty. Please upload a valid file.");
            error.put("status", "400");

            return ResponseEntity
                    .badRequest()
                    .body(error);
        }

        List<String> allowedTypes = Arrays.asList(
                "image/jpeg",
                "image/png",
                "application/pdf"
        );

        String fileType = file.getContentType();

//        if file is not in the given format

        if (fileType == null || !allowedTypes.contains(fileType)) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid file type. Allowed: JPG, PNG, PDF.");
            error.put("status", "400");
            return ResponseEntity.badRequest().body(error);
        }

//        if valid then upload

        FileResponse response = fileService.uploadFile(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/presigned/{fileName}")
    public ResponseEntity<?> getPresignedUrl(@PathVariable String fileName) {
        String url = fileService.generatePresignedUrl(fileName);
        if (url.startsWith("File does not exist")) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "File not found: " + fileName);
            error.put("status", "404");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(url);
    }
}
