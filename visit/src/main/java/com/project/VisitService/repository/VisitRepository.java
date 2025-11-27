package com.project.VisitService.repository;

import com.project.VisitService.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByDoctorIdAndStatusOrderByScheduledTimeAsc(Long doctorId, Visit.VisitStatus status);

    List<Visit> findByDoctorIdOrderByScheduledTimeAsc(Long doctorId);

    List<Visit> findByPatientIdOrderByScheduledTimeDesc(Long patientId);

    // last visit between patient & doctor (any status)
    Optional<Visit> findTopByDoctorIdAndPatientIdOrderByScheduledTimeDesc(Long doctorId, Long patientId);

    // last completed visit between patient & doctor
    Optional<Visit> findTopByDoctorIdAndPatientIdAndStatusOrderByScheduledTimeDesc(Long doctorId, Long patientId, Visit.VisitStatus status);

    List<Visit> findByPatientIdOrderByScheduledTimeAsc(Long patientId);

    List<Visit> findByDoctorIdAndScheduledTimeBetween(Long doctorId, LocalDateTime from, LocalDateTime to);
    List<Visit> findByPatientIdAndStatusOrderByScheduledTimeAsc(Long patientId, Visit.VisitStatus status);
    
    Optional<Visit> findTopByPatientIdAndDoctorIdOrderByScheduledTimeDesc(
            Long patientId, Long doctorId);
}
