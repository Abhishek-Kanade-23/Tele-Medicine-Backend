package com.project.PatientService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.PatientService.DTO.PatientDTO;
import com.project.PatientService.entities.Patient;
import com.project.PatientService.repositories.patient.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public List<PatientDTO> getAllPatients() {
		return patientRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public PatientDTO getPatientById(Long patientId) {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

		return convertToDTO(patient);
	}

	@Override
	public String addPatient(PatientDTO dto) {

		// Optional: basic duplicate check
		if (dto.getPatientId() != null && patientRepository.existsById(dto.getPatientId())) {
			return "Patient with ID " + dto.getPatientId() + " already exists";
		}

		Patient entity = Patient.builder().patientId(dto.getPatientId()).name(dto.getName()).age(dto.getAge())
				.gender(dto.getGender()).phone(dto.getPhone()).email(dto.getEmail()).address(dto.getAddress()).build();

		patientRepository.save(entity);

		return "Patient added to the database";
	}

	private PatientDTO convertToDTO(Patient patient) {
		PatientDTO dto = new PatientDTO();
		dto.setPatientId(patient.getPatientId());
		dto.setName(patient.getName());
		dto.setAge(patient.getAge());
		dto.setGender(patient.getGender());
		dto.setPhone(patient.getPhone());
		dto.setEmail(patient.getEmail());
		dto.setAddress(patient.getAddress());
		return dto;
	}

	@Override
	public String updatePatient(Long patientId, PatientDTO dto) {

		Patient existing = patientRepository.findById(patientId)
				.orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

		existing.setName(dto.getName());
		existing.setAge(dto.getAge());
		existing.setGender(dto.getGender());
		existing.setPhone(dto.getPhone());
		existing.setEmail(dto.getEmail());
		existing.setAddress(dto.getAddress());

		patientRepository.save(existing);
		return "Patient updated successfully";
	}

	@Override
	public String deletePatient(Long patientId) {

		if (!patientRepository.existsById(patientId)) {
			return "Patient not found with ID " + patientId;
		}

		patientRepository.deleteById(patientId);
		return "Patient deleted successfully";
	}

    @Override
    public PatientDTO checkPatientExists(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            // Return empty DTO
            return PatientDTO.builder()
                    .patientId(null)
                    .name("")
                    .age(null)
                    .gender("")
                    .phone("")
                    .email("")
                    .address("")
                    .build();
        }

        // Return actual user profile
        return PatientDTO.builder()
                .patientId(patient.getPatientId())
                .name(patient.getName())
                .age(patient.getAge())
                .gender(patient.getGender())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .build();
    }


}
