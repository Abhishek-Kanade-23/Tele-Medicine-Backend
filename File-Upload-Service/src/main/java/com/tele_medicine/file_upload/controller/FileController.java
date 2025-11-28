package com.tele_medicine.file_upload.controller;

import com.tele_medicine.file_upload.dto.FileResponse;
import com.tele_medicine.file_upload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tele_medicine.file_upload.util.JWTUtil;
import com.tele_medicine.file_upload.repository.MedicalDocumentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:5173") // your React frontend
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final JWTUtil jwtUtil;
    private final MedicalDocumentRepository repo;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam String documentType,
            @RequestParam(required = false) String description,
            @RequestParam String recordDate,
            @RequestHeader("Authorization") String token) {

        System.out.println("File Upload API HIT!");
        System.out.println("Received file = " + file.getOriginalFilename());
        System.out.println("documentType = " + documentType);
        System.out.println("description = " + description);
        System.out.println("recordDate = " + recordDate);
        FileResponse response = fileService.uploadAndSave(file, documentType, description, recordDate, token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient")
    public ResponseEntity<?> getDocs(@RequestHeader("Authorization") String token) {
        String patientId = jwtUtil.extractUserId(token);
        return ResponseEntity.ok(repo.findByPatientId(patientId));
    }

}
