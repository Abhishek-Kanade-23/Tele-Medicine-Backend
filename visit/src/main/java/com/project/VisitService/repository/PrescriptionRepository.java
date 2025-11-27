package com.project.VisitService.repository;

import com.project.VisitService.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findTopByVisitIdOrderByCreatedAtDesc(Long visitId);

}
