package com.authentication_service.services;


import com.authentication_service.dtos.DoctorCreateDTO;
import com.authentication_service.dtos.DoctorResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DOCTER-SERVICE")
public interface DoctorServiceClient {

    @PostMapping("/api/doctor/manage/create")
    DoctorResponseDTO createDoctor(@RequestBody DoctorCreateDTO dto);

    @GetMapping("/api/doctor/manage/check/{doctorId}")
    DoctorResponseDTO checkDoctorExists(@PathVariable("doctorId") Long doctorId);
}
