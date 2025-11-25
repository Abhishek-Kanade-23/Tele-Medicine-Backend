package com.telemed.doctor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${visit.service.base-url}")
    private String visitServiceBaseUrl;

    @Bean
    public WebClient visitServiceWebClient() {
        return WebClient.builder()
                .baseUrl(visitServiceBaseUrl)
                .build();
    }
}
