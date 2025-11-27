package com.project.VisitService.service;

import com.project.VisitService.model.Prescription;
import com.project.VisitService.repository.PrescriptionRepository;
import com.project.VisitService.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final VisitRepository visitRepository;

    @Override
    @Transactional
    public Long createPrescriptionForVisit(Long visitId) {
        // validate visit exists
        visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found: " + visitId));

        Prescription p = Prescription.builder()
                .visitId(visitId)
                .build();

        Prescription saved = prescriptionRepository.save(p);
        return saved.getPrescriptionId();
    }
}
