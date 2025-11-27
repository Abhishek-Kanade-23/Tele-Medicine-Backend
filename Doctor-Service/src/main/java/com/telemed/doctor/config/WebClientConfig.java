package com.telemed.doctor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Visit-Service base
    @Bean
    public WebClient visitServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8085/api")  // standardized: Visit endpoints under /api/...
                .exchangeStrategies(ExchangeStrategies.builder().build())
                .build();
    }

    // Patient-Service base
    @Bean
    public WebClient patientWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082/api") // standardized: Patient endpoints under /api/patients
                .exchangeStrategies(ExchangeStrategies.builder().build())
                .build();
    }
}
