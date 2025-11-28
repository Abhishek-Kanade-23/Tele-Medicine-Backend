package com.tele_medicine.file_upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tele_medicine.file_upload.entity.MedicalDocument;

import java.util.List;

public interface MedicalDocumentRepository extends JpaRepository<MedicalDocument, Long> {
    List<MedicalDocument> findByPatientId(String patientId);
}
