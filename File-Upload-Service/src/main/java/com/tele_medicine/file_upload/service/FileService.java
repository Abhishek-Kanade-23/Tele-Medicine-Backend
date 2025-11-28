package com.tele_medicine.file_upload.service;

import com.tele_medicine.file_upload.dto.FileResponse;
import com.tele_medicine.file_upload.entity.MedicalDocument;
import com.tele_medicine.file_upload.repository.MedicalDocumentRepository;
import com.tele_medicine.file_upload.util.JWTUtil;

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

@Service
@RequiredArgsConstructor
public class FileService {

        private final S3Client s3Client;
        private final S3Presigner presigner;
        private final JWTUtil jwtUtil;
        private final MedicalDocumentRepository repo;

        @Value("${aws.s3.bucket}")
        private String bucketName;

        public FileResponse uploadAndSave(
                        MultipartFile file,
                        String docType,
                        String desc,
                        String recordDate,
                        String token) {
                try {
                        String patientId = jwtUtil.extractUserId(token);

                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                        PutObjectRequest putRequest = PutObjectRequest.builder()
                                        .bucket(bucketName)
                                        .key(fileName)
                                        .contentType(file.getContentType())
                                        .build();

                        s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

                        // Generate S3 URL
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

                        // Save metadata in DB
                        MedicalDocument doc = MedicalDocument.builder()
                                        .patientId(patientId)
                                        .documentType(docType)
                                        .description(desc)
                                        .recordDate(LocalDate.parse(recordDate))
                                        .uploadedAt(LocalDate.now())
                                        .fileName(fileName)
                                        .fileUrl(presignedUrl.toString())
                                        .build();

                        repo.save(doc);

                        return FileResponse.builder()
                                        .id(doc.getId())
                                        .fileName(doc.getFileName())
                                        .fileUrl(doc.getFileUrl())
                                        .documentType(doc.getDocumentType())
                                        .description(doc.getDescription())
                                        .recordDate(doc.getRecordDate().toString())
                                        .uploadedAt(doc.getUploadedAt().toString())
                                        .build();

                } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("Upload failed: " + e.getMessage());
                }
        }
}
