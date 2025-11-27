package com.project.PatientService.repositories.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.PatientService.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
