package com.authentication_service.services;


import com.authentication_service.dtos.DoctorCreateDTO;
import com.authentication_service.dtos.DoctorProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "docter-service")
public interface DoctorServiceClient {

    @PostMapping("/doctor/manage/create")
    DoctorProfileDTO createDoctor(@RequestBody DoctorCreateDTO dto);

    @GetMapping("/doctor/manage/check/{doctorId}")
    DoctorProfileDTO checkDoctorExists(@PathVariable("doctorId") Long doctorId);
}
