package com.project.PatientService.controllers;

import com.project.PatientService.DTO.PatientDTO;
import com.project.PatientService.DTO.PatientResponseDTO;
import com.project.PatientService.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<PatientResponseDTO> addPatient(@RequestBody PatientDTO patient) {


		return ResponseEntity.status(HttpStatus.CREATED).body(patientService.addPatient(patient));
	}

	@PutMapping("/{patientId}")
	public ResponseEntity<String> updatePatient(@PathVariable Long patientId, @RequestBody PatientDTO dto) {
		return ResponseEntity.ok(patientService.updatePatient(patientId, dto));
	}

	@DeleteMapping("/{patientId}")
	public ResponseEntity<String> deletePatient(@PathVariable Long patientId) {
		return ResponseEntity.ok(patientService.deletePatient(patientId));
	}
    @GetMapping("/check/{patientId}")
    public ResponseEntity<PatientDTO> checkPatientExists(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.checkPatientExists(patientId));
    }

}
