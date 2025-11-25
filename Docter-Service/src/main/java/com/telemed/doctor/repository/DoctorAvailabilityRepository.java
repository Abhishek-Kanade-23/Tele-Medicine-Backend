package com.telemed.doctor.repository;

import com.telemed.doctor.model.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

    // Get all availability slots for a doctor
    List<DoctorAvailability> findByDoctorId(Long doctorId);

    // Fetch availability for a specific date
    List<DoctorAvailability> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate availableDate);

    // Delete an availability slot for a doctor
    void deleteByDoctorIdAndId(Long doctorId, Long id);
}
