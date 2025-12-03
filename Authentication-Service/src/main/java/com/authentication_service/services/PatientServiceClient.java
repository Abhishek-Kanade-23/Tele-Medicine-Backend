package com.authentication_service.services;

import com.authentication_service.dtos.PatientProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientServiceClient {

    @GetMapping("/patients/check/{patientId}")
    PatientProfileDTO checkPatientExists(@PathVariable("patientId") Long patientId);

}

