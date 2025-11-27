package com.telemed.doctor.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PatientServiceClient {

    private final WebClient patientWebClient;

    /**
     * Returns patient name or "Unknown" if not found or remote call fails.
     */
    public String getPatientName(Long patientId) {
        try {
            Mono<PatientResponse> mono = patientWebClient.get()
                    .uri("/patients/{id}", patientId)
                    .retrieve()
                    .bodyToMono(PatientResponse.class)
                    .retry(1)
                    .timeout(Duration.ofSeconds(3))
                    .onErrorResume(e -> Mono.empty());

            PatientResponse dto = mono.block();
            if (dto == null) return "Unknown";
            return dto.getName() != null ? dto.getName() : "Unknown";
        } catch (Exception e) {
            System.out.println("⚠ Doctor-Service: Failed to fetch patient name → " + e.getMessage());
            return "Unknown";
        }
    }

    // DTO to match Patient-Service response
    private static class PatientResponse {
        private Long patientId;
        private String name;

        public Long getPatientId() { return patientId; }
        public void setPatientId(Long patientId) { this.patientId = patientId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
