package com.telemed.doctor.repository;

import com.telemed.doctor.model.DoctorPatientMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorPatientMapRepository extends JpaRepository<DoctorPatientMap, Long> {

    // Fetch all patients mapped to a specific doctor
    List<DoctorPatientMap> findByDoctorId(Long doctorId);

    // Check if a patient is actually assigned to this doctor
    boolean existsByDoctorIdAndPatientId(Long doctorId, Long patientId);

    // Get mapping for a specific doctor & patient
    DoctorPatientMap findByDoctorIdAndPatientId(Long doctorId, Long patientId);
}
