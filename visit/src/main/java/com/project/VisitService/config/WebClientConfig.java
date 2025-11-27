package com.project.VisitService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Doctor-Service base now standardized under /api/doctors
    @Bean
    public WebClient doctorWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8084/api")   // -> /api/doctors/...
                .exchangeStrategies(ExchangeStrategies.builder().build())
                .build();
    }

    // Patient-Service base now standardized under /api/patients
    @Bean
    public WebClient patientWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082")   // -> /api/patients/...
                .exchangeStrategies(ExchangeStrategies.builder().build())
                .build();
    }
}
