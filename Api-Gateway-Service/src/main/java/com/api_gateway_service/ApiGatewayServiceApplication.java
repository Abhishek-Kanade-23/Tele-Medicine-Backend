package com.api_gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.api_gateway_service")
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}

}
