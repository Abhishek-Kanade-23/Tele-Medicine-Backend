// Feature branch 
package com.tele_medicine.file_upload.service;

import com.tele_medicine.file_upload.dto.DoctorFileResponse;
import com.tele_medicine.file_upload.dto.FileResponse;
import com.tele_medicine.file_upload.entity.MedicalDocument;
import com.tele_medicine.file_upload.repository.MedicalDocumentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

        private final S3Client s3Client;
        private final S3Presigner presigner;
        private final MedicalDocumentRepository repo;

        @Value("${aws.s3.bucket}")
        private String bucketName;

        // UPLOAD & SAVE METADATA

        public FileResponse uploadAndSave(
                        MultipartFile file,
                        String docType,
                        String desc,
                        String recordDate,
                        String token) {

                try {
                        String patientId = token;
                        System.out.println(patientId);

                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                        PutObjectRequest putRequest = PutObjectRequest.builder()
                                        .bucket(bucketName)
                                        .key(fileName)
                                        .contentType(file.getContentType())
                                        .build();

                        s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

                        MedicalDocument doc = MedicalDocument.builder()
                                        .patientId(patientId)
                                        .documentType(docType)
                                        .description(desc)
                                        .recordDate(LocalDate.parse(recordDate))
                                        .uploadedAt(LocalDate.now())
                                        .fileName(fileName)
                                        .build();

                        repo.save(doc);

                        return FileResponse.builder()
                                        .id(doc.getId())
                                        .fileName(doc.getFileName())
                                        .documentType(doc.getDocumentType())
                                        .description(doc.getDescription())
                                        .recordDate(doc.getRecordDate().toString())
                                        .uploadedAt(doc.getUploadedAt().toString())
                                        .build();

                } catch (Exception e) {
                        throw new RuntimeException("Upload failed: " + e.getMessage());
                }
        }

        // GENERATE FRESH PRESIGNED URL
        public FileResponse generateDownloadUrl(Long id) {

                MedicalDocument doc = repo.findById(id)
                                .orElseThrow(() -> new RuntimeException("Document not found"));

                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                .bucket(bucketName)
                                .key(doc.getFileName())
                                .build();

                URL url = presigner.presignGetObject(
                                GetObjectPresignRequest.builder()
                                                .getObjectRequest(getObjectRequest)
                                                .signatureDuration(Duration.ofMinutes(10)) // Fresh URL
                                                .build())
                                .url();

                return FileResponse.builder()
                                .id(doc.getId())
                                .fileName(doc.getFileName())
                                .fileUrl(url.toString()) // finally added here
                                .build();
        }

        // LIST ALL FILES FOR A PATIENT
        public List<FileResponse> getAllFiles(String patientId) {
                List<MedicalDocument> documents = repo.findByPatientId(patientId);
                return documents.stream().map(doc -> FileResponse.builder()
                                .id(doc.getId())
                                .fileName(doc.getFileName())
                                .documentType(doc.getDocumentType())
                                .description(doc.getDescription())
                                .recordDate(doc.getRecordDate().toString())
                                .uploadedAt(doc.getUploadedAt().toString())
                                .build()).toList();
        }

        // GENERATE PRESIGNED URL FOR GIVEN FILENAME AND PATIENT ID
        public String generatePresignedUrl(String fileName, String patientId) {

                // Optional: check DB to make sure file belongs to this patient
                MedicalDocument doc = repo.findByFileName(fileName);
                if (doc == null || !doc.getPatientId().equals(patientId)) {
                        throw new RuntimeException("Unauthorized or file not found");
                }

                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                .bucket(bucketName)
                                .key(fileName)
                                .build();

                URL presignedUrl = presigner.presignGetObject(
                                GetObjectPresignRequest.builder()
                                                .getObjectRequest(getObjectRequest)
                                                .signatureDuration(Duration.ofMinutes(10))
                                                .build())
                                .url();

                return presignedUrl.toString();
        }

        // DELETE FILE
        public void deleteFile(Long documentId) {
                MedicalDocument doc = repo.findById(documentId)
                                .orElseThrow(() -> new RuntimeException("Document not found"));
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                                .bucket(bucketName)
                                .key(doc.getFileName())
                                .build();

                s3Client.deleteObject(deleteObjectRequest);

                repo.delete(doc);
        }

        // list all documents for doctor view
        public List<DoctorFileResponse> getAllDocumentsForDoctor() {
                return repo.findAll().stream()
                                .map(doc -> DoctorFileResponse.builder()
                                                .id(doc.getId())
                                                .fileName(doc.getFileName())
                                                .documentType(doc.getDocumentType())
                                                .description(doc.getDescription())
                                                .recordDate(doc.getRecordDate().toString())
                                                .uploadedAt(doc.getUploadedAt().toString())
                                                .patientId(doc.getPatientId()) // ★ include here
                                                .build())
                                .toList();
        }

        // generate presigned URL for doctor
        public String generatePresignedUrlForDoctor(String fileName) {
                MedicalDocument doc = repo.findByFileName(fileName);
                if (doc == null) {
                        throw new RuntimeException("File not found");
                }

                URL url = presigner.presignGetObject(
                                GetObjectPresignRequest.builder()
                                                .getObjectRequest(
                                                                GetObjectRequest.builder()
                                                                                .bucket(bucketName)
                                                                                .key(doc.getFileName())
                                                                                .build())
                                                .signatureDuration(Duration.ofMinutes(10))
                                                .build())
                                .url();

                return url.toString();
        }

}
