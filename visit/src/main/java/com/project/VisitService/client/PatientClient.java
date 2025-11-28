package com.project.VisitService.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PatientClient {

    private final WebClient patientWebClient;

    /**
     * Returns patient name or null when not found / on error.
     */
    public String getPatientName(Long patientId) {
        try {
            Mono<com.project.VisitService.dto.PatientDTO> mono = patientWebClient.get()
                    .uri("/patients/{id}", patientId)
                    .retrieve()
                    .bodyToMono(com.project.VisitService.dto.PatientDTO.class)
                    .retry(1)
                    .timeout(Duration.ofSeconds(3))
                    .onErrorResume(e -> Mono.empty());

            com.project.VisitService.dto.PatientDTO dto = mono.block();
            return dto == null ? null : dto.getFirstName();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return PatientDTO or null
     */
    public com.project.VisitService.dto.PatientDTO getPatient(Long patientId) {
        try {
            Mono<com.project.VisitService.dto.PatientDTO> mono = patientWebClient.get()
                    .uri("/patients/{id}", patientId)
                    .retrieve()
                    .bodyToMono(com.project.VisitService.dto.PatientDTO.class)
                    .retry(1)
                    .timeout(Duration.ofSeconds(3))
                    .onErrorResume(e -> Mono.empty());

            return mono.block();
        } catch (Exception e) {
            return null;
        }
    }
}
