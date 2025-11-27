package com.project.VisitService.service;

import com.project.VisitService.model.Consultation;
import com.project.VisitService.model.Visit;
import com.project.VisitService.repository.ConsultationRepository;
import com.project.VisitService.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final VisitRepository visitRepository;

    @Override
    @Transactional
    public Consultation addConsultation(Long visitId, String notes, LocalDate followUpDate) {

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found: " + visitId));

        Consultation.ConsultationBuilder builder = Consultation.builder()
                .visitId(visitId)
                .notes(notes)
                .followUpDate(followUpDate);

        Consultation consultation = builder.build();
        Consultation saved = consultationRepository.save(consultation);

        // mark visit completed
        visit.setStatus(Visit.VisitStatus.COMPLETED);
        visitRepository.save(visit);

        return saved;
    }

    @Override
    @Transactional
    public Consultation updateConsultation(Long consultationId, String notes, LocalDate followUpDate) {
        Consultation cons = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation not found: " + consultationId));

        if (notes != null) cons.setNotes(notes);

        // followUpDate: replace value (null allowed)
        cons.setFollowUpDate(followUpDate);

        return consultationRepository.save(cons);
    }
}
