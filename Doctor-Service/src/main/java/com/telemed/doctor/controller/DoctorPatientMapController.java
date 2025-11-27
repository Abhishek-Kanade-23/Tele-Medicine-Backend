package com.telemed.doctor.controller;

import com.telemed.doctor.service.DoctorPatientMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorPatientMapController {

    private final DoctorPatientMapService doctorPatientMapService;

    @PostMapping("/map")
    public ResponseEntity<String> mapDoctorAndPatient(
            @RequestParam Long doctorId,
            @RequestParam Long patientId) {

        doctorPatientMapService.createOrUpdateMapping(doctorId, patientId);
        return ResponseEntity.ok("Mapping saved successfully");
    }
}
