package com.tele_medicine.file_upload.controller;

import com.tele_medicine.file_upload.dto.FileResponse;
import com.tele_medicine.file_upload.dto.DoctorFileResponse;
import com.tele_medicine.file_upload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final MedicalDocumentRepository repo;

    // To handle file upload
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam String documentType,
            @RequestParam(required = false) String description,
            @RequestParam String recordDate,
            @RequestHeader("X-Patient-Id") String token) {

        System.out.println("File Upload API HIT!");
        System.out.println("Received file = " + file.getOriginalFilename());
        System.out.println("documentType = " + documentType);
        System.out.println("description = " + description);
        System.out.println("recordDate = " + recordDate);
        FileResponse response = fileService.uploadAndSave(file, documentType, description, recordDate, token);
        return ResponseEntity.ok(response);
    }

    // To generate pre-signed download URL
    @GetMapping("/download-url")
    public ResponseEntity<String> getDownloadUrl(
            @RequestParam String fileName,
            @RequestHeader("X-Patient-Id") String patientId) {
        String url = fileService.generatePresignedUrl(fileName, patientId);
        return ResponseEntity.ok(url);
    }

    // to return list of files
    @GetMapping("/list")
    public List<FileResponse> getFiles(@RequestHeader("X-Patient-Id") String patientId) {
        return fileService.getAllFiles(patientId);
    }

    // to delete a file
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // to return list of files for doctor
    @GetMapping("/doctor/all")
    public List<DoctorFileResponse> getAllDocumentsForDoctor() {
        return fileService.getAllDocumentsForDoctor();
    }

    // to generate pre-signed download URL for doctor
    @GetMapping("/doctor/download-url")
    public ResponseEntity<String> getDoctorDownloadUrl(@RequestParam String fileName) {
        String url = fileService.generatePresignedUrlForDoctor(fileName);
        return ResponseEntity.ok(url);
    }

}
