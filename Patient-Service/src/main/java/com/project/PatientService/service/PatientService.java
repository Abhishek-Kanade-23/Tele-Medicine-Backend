package com.project.PatientService.service;

import java.util.List;

import com.project.PatientService.DTO.PatientDTO;

public interface PatientService {

	List<PatientDTO> getAllPatients();
	PatientDTO getPatientById(Long patientId);
	String addPatient(PatientDTO patient);
	String updatePatient(Long patientId, PatientDTO patient);
    String deletePatient(Long patientId);
    PatientDTO checkPatientExists(Long patientId);

}
