package com.project.PatientService.controllers;

import com.project.PatientService.DTO.BookVisitDTO;
import com.project.PatientService.DTO.DoctorDTO;
import com.project.PatientService.DTO.AvailabilityDTO;
import com.project.PatientService.client.DoctorServiceClient;
import com.project.PatientService.service.VisitViewService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final DoctorServiceClient doctorClient;
    private final VisitViewService visitService;

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        return ResponseEntity.ok(doctorClient.getAllDoctors());
    }

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<List<AvailabilityDTO>> getDoctorAvailability(
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorClient.getAvailability(doctorId));
    }
}
