package com.project.VisitService.client;

import com.project.VisitService.dto.DoctorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class DoctorClient {

    private final WebClient doctorWebClient;

    /**
     * Correct: Doctor-Service endpoint is /api/doctor/manage/{doctorId}
     */
    public DoctorDTO getDoctor(Long doctorId) {
        try {
            return doctorWebClient.get()
                    .uri("/doctor/manage/{doctorId}", doctorId)
                    .retrieve()
                    .bodyToMono(DoctorDTO.class)
                    .timeout(Duration.ofSeconds(3))
                    .retry(1)
                    .onErrorResume(e -> Mono.empty())
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return complete doctor name or null.
     */
    public String getDoctorName(Long doctorId) {
        DoctorDTO dto = getDoctor(doctorId);
        if (dto == null) return null;

        String first = dto.getFirstName() != null ? dto.getFirstName() : "";
        String last = dto.getLastName() != null ? dto.getLastName() : "";

        return (first + " " + last).trim();
    }

    /**
     * Mapping endpoint (DISABLED unless you add this route)
     * If added later, correct Doctor-Service URL must be:
     * POST /api/doctor/{doctorId}/patients?patientId=...
     */
    public void createMapping(Long doctorId, Long patientId) {
        try {
            doctorWebClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/doctor/{doctorId}/patients")
                            .queryParam("patientId", patientId)
                            .build(doctorId))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(3))
                    .retry(1)
                    .onErrorResume(e -> Mono.empty())
                    .block();
        } catch (Exception ignored) {}
    }
}
