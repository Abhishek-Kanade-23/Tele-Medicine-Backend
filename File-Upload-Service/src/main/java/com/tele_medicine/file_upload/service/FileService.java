package com.tele_medicine.file_upload.service;

import com.tele_medicine.file_upload.dto.FileResponse;
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

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Client s3Client;
    private final S3Presigner presigner;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public FileResponse uploadFileAndGenerateUrl(MultipartFile file) {
        try {
            // 1. Generate unique file name
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // 2. Upload to S3
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

            // 3. Generate presigned URL
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            GetObjectPresignRequest presignRequest =
                    GetObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(10))
                            .getObjectRequest(getObjectRequest)
                            .build();

            URL url = presigner.presignGetObject(presignRequest).url();

            // 4. Return response object
            return new FileResponse(fileName, "Uploaded Successfully", url.toString());

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }
}
