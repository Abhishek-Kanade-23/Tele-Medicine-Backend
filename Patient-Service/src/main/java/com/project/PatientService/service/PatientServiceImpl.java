package com.project.PatientService.service;

import java.util.List;
import java.util.stream.Collectors;

import com.project.PatientService.DTO.PatientResponseDTO;
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
	public PatientResponseDTO addPatient(PatientDTO dto) {

        System.out.println("Received Req ==>  " + dto  );

		// Optional: basic duplicate check
		if (dto.getPatientId() != null && patientRepository.existsById(dto.getPatientId())) {
            System.out.println("Patient with ID " + dto.getPatientId() + " already exists");
            return new PatientResponseDTO() ;
		}

		Patient entity = Patient.builder()
                .patientId(dto.getPatientId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
				.gender(dto.getGender())
                .phone(dto.getPhone())
                .emailId(dto.getEmailId())
                .address(dto.getAddress())
                .weight(dto.getWeight())
                .bloodGroup(dto.getBloodGroup())
                .build();

		Patient createdPatient = patientRepository.save(entity);

		return new PatientResponseDTO(
                createdPatient.getPatientId(),
                createdPatient.getFirstName(),
                createdPatient.getLastName(),
                createdPatient.getAge() ,
                createdPatient.getGender(),
                createdPatient.getPhone(),
                createdPatient.getEmailId(),
                createdPatient.getAddress(),
                createdPatient.getWeight(),
                createdPatient.getBloodGroup()
        );
	}

	private PatientDTO convertToDTO(Patient patient) {
		PatientDTO dto = new PatientDTO();
		dto.setPatientId(patient.getPatientId());
		dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
		dto.setAge(patient.getAge());
		dto.setGender(patient.getGender());
		dto.setPhone(patient.getPhone());
		dto.setEmailId(patient.getEmailId());
		dto.setAddress(patient.getAddress());
        dto.setWeight(patient.getWeight());
        dto.setBloodGroup(patient.getBloodGroup());
		return dto;
	}

	@Override
	public String updatePatient(Long patientId, PatientDTO dto) {

		Patient existing = patientRepository.findById(patientId)
				.orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

		existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
		existing.setAge(dto.getAge());
		existing.setGender(dto.getGender());
		existing.setPhone(dto.getPhone());
		existing.setEmailId(dto.getEmailId());
		existing.setAddress(dto.getAddress());
        existing.setWeight(dto.getWeight());
        existing.setBloodGroup(dto.getBloodGroup());
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
                    .firstName("")
                    .lastName("")
                    .age(null)
                    .gender("")
                    .phone("")
                    .emailId("")
                    .address("")
                    .weight(0.0f)
                    .bloodGroup("")
                    .build();
        }

        // Return actual user profile
        return PatientDTO.builder()
                .patientId(patient.getPatientId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .age(patient.getAge())
                .gender(patient.getGender())
                .phone(patient.getPhone())
                .emailId(patient.getEmailId())
                .address(patient.getAddress())
                .bloodGroup(patient.getBloodGroup())
                .build();
    }


}
