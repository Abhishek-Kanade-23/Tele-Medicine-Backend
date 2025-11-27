package com.project.PatientService.client;

import com.project.PatientService.DTO.BookVisitDTO;
import com.project.PatientService.DTO.VisitDetailsDTO;
import com.project.PatientService.DTO.VisitHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitServiceClient {

    private final WebClient visitWebClient;

    // Book visit -> POST /api/visits
    public Object bookVisit(BookVisitDTO dto) {
        try {
            return visitWebClient.post()
                    .uri("/visits")
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

    // Upcoming visits for patient -> GET /api/patients/{id}/visits/upcoming
    public List<VisitHistoryDTO> getUpcomingVisits(Long patientId) {
        return visitWebClient.get()
                .uri("/patients/{id}/visits/upcoming", patientId)
                .retrieve()
                .bodyToFlux(VisitHistoryDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();
    }

    // Visit history -> GET /api/patients/{id}/visits/history
    public List<VisitHistoryDTO> getVisitHistory(Long patientId) {
        return visitWebClient.get()
                .uri("/patients/{id}/visits/history", patientId)
                .retrieve()
                .bodyToFlux(VisitHistoryDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();
    }

    // Visit details -> GET /api/visits/{visitId}
    public VisitDetailsDTO getVisitDetails(Long visitId) {
        return visitWebClient.get()
                .uri("/visits/{visitId}", visitId)
                .retrieve()
                .bodyToMono(VisitDetailsDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(e -> Mono.empty())
                .block();
    }
}
