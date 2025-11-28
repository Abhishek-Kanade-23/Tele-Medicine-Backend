package com.tele_medicine.file_upload.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientId;      // From JWT
    private String documentType;
    @Column(length = 2000)
    private String description;

    private LocalDate recordDate;
    private LocalDate uploadedAt;

    @Column(length = 5000)
private String fileUrl;

@Column(length = 1000)
private String fileName;


      // S3 link
}
