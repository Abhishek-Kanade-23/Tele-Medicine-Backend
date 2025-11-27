package com.project.PatientService.client;

import com.project.PatientService.DTO.AvailabilityDTO;
import com.project.PatientService.DTO.DoctorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorServiceClient {

    private final WebClient doctorWebClient;

    // GET /api/doctors
    public List<DoctorDTO> getAllDoctors() {
        return doctorWebClient.get()
                .uri("/doctor/manage/all")
                .retrieve()
                .bodyToFlux(DoctorDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();
    }

    // GET /api/doctors/{doctorId}/availability
    public List<AvailabilityDTO> getAvailability(Long doctorId) {
        return doctorWebClient.get()
                .uri("/doctor/availability/{doctorId}", doctorId)
                .retrieve()
                .bodyToFlux(AvailabilityDTO.class)
                .retry(1)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();
    }

    // GET single doctor
    public DoctorDTO getDoctor(Long doctorId) {
        try {
            Mono<DoctorDTO> mono = doctorWebClient.get()
                    .uri("/doctor/manage/{doctorId}", doctorId)
                    .retrieve()
                    .bodyToMono(DoctorDTO.class)
                    .retry(1)
                    .timeout(Duration.ofSeconds(3))
                    .onErrorResume(e -> Mono.empty());
            return mono.block();
        } catch (Exception e) {
            return null;
        }
    }
}
