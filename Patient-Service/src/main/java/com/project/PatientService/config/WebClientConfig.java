package com.project.PatientService.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Visit-Service
    @Bean
    public WebClient visitWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8085/api")
                .exchangeStrategies(ExchangeStrategies.builder().build())
                .build();
    }

    // Doctor-Service
    @Bean
    public WebClient doctorWebClient() {
        return WebClient.builder()
        		.baseUrl("http://localhost:8084/")
                .exchangeStrategies(ExchangeStrategies.builder().build())
                .build();
    }
}
