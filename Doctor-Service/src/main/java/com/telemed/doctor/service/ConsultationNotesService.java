package com.telemed.doctor.service;

import com.telemed.doctor.client.VisitServiceClient;
import com.telemed.doctor.model.dto.ConsultationCreateDTO;
import com.telemed.doctor.model.dto.ConsultationResponseDTO;
import com.telemed.doctor.model.dto.ConsultationUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultationNotesService {

    private final VisitServiceClient visitServiceClient;

    /**
     * Create consultation notes on Visit-Service.
     * This method catches remote errors and returns a friendly message instead of letting exceptions
     * propagate and potentially mark a transaction as rollback-only.
     */
    public ConsultationResponseDTO createConsultationNotes(Long visitId, ConsultationCreateDTO dto) {
        return visitServiceClient.createConsultation(visitId, dto);
    }

    /**
     * Update consultation notes on Visit-Service.
     */
    public ConsultationResponseDTO updateConsultationNotes(Long consultationId, ConsultationUpdateDTO dto) {
        return visitServiceClient.updateConsultation(consultationId, dto);
    }
}
