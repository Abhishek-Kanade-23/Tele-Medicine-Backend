package com.project.PatientService.service;

import java.util.List;

import com.project.PatientService.DTO.PatientDTO;
import com.project.PatientService.DTO.PatientResponseDTO;

public interface PatientService {

	List<PatientDTO> getAllPatients();
	PatientDTO getPatientById(Long patientId);
	PatientResponseDTO addPatient(PatientDTO patient);
	PatientResponseDTO updatePatient(Long patientId, PatientDTO patient);
    String deletePatient(Long patientId);
    PatientDTO checkPatientExists(Long patientId);

}
