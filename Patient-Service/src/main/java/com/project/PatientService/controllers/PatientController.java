package com.project.PatientService.controllers;

import com.project.PatientService.DTO.PatientDTO;
import com.project.PatientService.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @PostMapping
    public ResponseEntity<String> addPatient(@RequestBody PatientDTO patient) {
        return ResponseEntity.ok(patientService.addPatient(patient));
    }
}
