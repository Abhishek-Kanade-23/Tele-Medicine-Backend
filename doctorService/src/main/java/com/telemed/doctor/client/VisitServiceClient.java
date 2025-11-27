package com.telemed.doctor.client;

import com.telemed.doctor.model.dto.LastVisitSummaryDTO;
import com.telemed.doctor.model.dto.VisitSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitServiceClient {

    private final WebClient visitServiceWebClient;

    // 1. Get Upcoming Visits for Doctor
    public List<VisitSummaryDTO> getUpcomingVisitsForDoctor(Long doctorId) {
        return visitServiceWebClient.get()
                .uri("/doctors/{doctorId}/visits/upcoming", doctorId)
                .retrieve()
                .bodyToFlux(VisitSummaryDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();
    }

    // 2. Get Last Visit Summary for Patient (global)
    public LastVisitSummaryDTO getLastVisitSummary(Long patientId) {
        return visitServiceWebClient.get()
                .uri("/patients/{patientId}/visits/last", patientId)
                .retrieve()
                .bodyToMono(LastVisitSummaryDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(e -> Mono.empty())
                .block();
    }

    // 3. Get Last Visit Summary for doctor+patient
    public LastVisitSummaryDTO getLastVisitSummaryForDoctorAndPatient(Long doctorId, Long patientId) {
        return visitServiceWebClient.get()
                .uri("/doctors/{doctorId}/patients/{patientId}/visits/last", doctorId, patientId)
                .retrieve()
                .bodyToMono(LastVisitSummaryDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(e -> Mono.empty())
                .block();
    }

    // 4. Get Visit History for patient
    public List<VisitSummaryDTO> getVisitHistory(Long patientId) {
        return visitServiceWebClient.get()
                .uri("/patients/{patientId}/visits/history", patientId)
                .retrieve()
                .bodyToFlux(VisitSummaryDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();
    }

    // 5. Create / Update consultation (we still call Visit-Service endpoints for consultations under visits)
    // create
    public Object createConsultation(Long visitId, Object dto) {
        try {
            return visitServiceWebClient.post()
                    .uri("/visits/{visitId}/consultation", visitId)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .retry(1)
                    .timeout(Duration.ofSeconds(3))
                    .block();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // update
    public Object updateConsultation(Long consultationId, Object dto) {
        try {
            return visitServiceWebClient.put()
                    .uri("/visits/{visitId}/consultation/{consultationId}", consultationId, consultationId) // if needed adjust path
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .retry(1)
                    .timeout(Duration.ofSeconds(3))
                    .block();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
