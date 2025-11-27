package com.telemed.doctor.service;

import com.telemed.doctor.client.VisitServiceClient;
import com.telemed.doctor.model.dto.ConsultationCreateDTO;
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
    public String createConsultationNotes(Long visitId, ConsultationCreateDTO dto) {
        try {
            // The Visit service returns a consultation object; we ask the client for a String-friendly result.
            return (String) visitServiceClient.createConsultation(visitId, dto);
        } catch (Exception e) {
            // Log and return message suitable for controller response
            String msg = "Failed to create consultation: " + e.getMessage();
            System.out.println("⚠ ConsultationNotesService.createConsultationNotes -> " + msg);
            return msg;
        }
    }

    /**
     * Update consultation notes on Visit-Service.
     */
    public String updateConsultationNotes(Long consultationId, ConsultationUpdateDTO dto) {
        try {
            return (String) visitServiceClient.updateConsultation(consultationId, dto);
        } catch (Exception e) {
            String msg = "Failed to update consultation: " + e.getMessage();
            System.out.println("⚠ ConsultationNotesService.updateConsultationNotes -> " + msg);
            return msg;
        }
    }
}
