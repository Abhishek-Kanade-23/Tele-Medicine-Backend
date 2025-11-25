package com.telemed.doctor.client;

import com.telemed.doctor.model.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitServiceClient {

    private final WebClient visitServiceWebClient;

    // --------------------------------------------------------
    // 1. Get Upcoming Visits for Doctor
    // --------------------------------------------------------
    public List<VisitSummaryDTO> getUpcomingVisitsForDoctor(Long doctorId) {
        return visitServiceWebClient.get()
                .uri("/doctor/{doctorId}/upcoming", doctorId)
                .retrieve()
                .bodyToFlux(VisitSummaryDTO.class)
                .collectList()
                .block();
    }

    // --------------------------------------------------------
    // 2. Get Last Visit Summary for Patient
    // --------------------------------------------------------
    public LastVisitSummaryDTO getLastVisitSummary(Long patientId) {
        return visitServiceWebClient.get()
                .uri("/patient/{patientId}/last", patientId)
                .retrieve()
                .bodyToMono(LastVisitSummaryDTO.class)
                .block();
    }

    // --------------------------------------------------------
    // 3. Get Full Visit History for a Patient
    // --------------------------------------------------------
    public List<VisitSummaryDTO> getVisitHistory(Long patientId) {
        return visitServiceWebClient.get()
                .uri("/patient/{patientId}/history", patientId)
                .retrieve()
                .bodyToFlux(VisitSummaryDTO.class)
                .collectList()
                .block();
    }

    // --------------------------------------------------------
    // 4. Create Consultation
    // --------------------------------------------------------
    public String createConsultation(Long visitId, ConsultationCreateDTO dto) {
        return visitServiceWebClient.post()
                .uri("/{visitId}/consultation", visitId)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // --------------------------------------------------------
    // 5. Update Consultation
    // --------------------------------------------------------
    public String updateConsultation(Long consultationId, ConsultationUpdateDTO dto) {
        return visitServiceWebClient.put()
                .uri("/consultation/{consultationId}", consultationId)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
