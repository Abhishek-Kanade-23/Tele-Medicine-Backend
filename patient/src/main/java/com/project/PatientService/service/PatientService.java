package com.project.PatientService.service;

import java.util.List;

import com.project.PatientService.DTO.PatientDTO;

public interface PatientService {

	List<PatientDTO> getAllPatients();
	PatientDTO getPatientById(Long patientId);
	String addPatient(PatientDTO patient);
	
}
