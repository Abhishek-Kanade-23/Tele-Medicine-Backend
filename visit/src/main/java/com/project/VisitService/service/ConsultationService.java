package com.project.VisitService.service;

import com.project.VisitService.model.Consultation;

import java.time.LocalDate;

public interface ConsultationService {

    Consultation addConsultation(Long visitId, String notes, LocalDate followUpDate);

    Consultation updateConsultation(Long consultationId, String notes, LocalDate followUpDate);
}
