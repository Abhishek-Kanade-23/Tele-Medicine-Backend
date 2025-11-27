package com.project.VisitService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/api/notifications/appointment-booked")
    Map<String, String> sendAppointmentEmails(@RequestBody Map<String, String> requestBody);
}
