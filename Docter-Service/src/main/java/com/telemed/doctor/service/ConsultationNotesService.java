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

    public String createConsultationNotes(Long visitId, ConsultationCreateDTO dto) {

        return visitServiceClient.createConsultation(visitId, dto);
    }

    public String updateConsultationNotes(Long consultationId, ConsultationUpdateDTO dto) {

        return visitServiceClient.updateConsultation(consultationId, dto);
    }
}
