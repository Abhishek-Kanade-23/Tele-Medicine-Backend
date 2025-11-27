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
        return patientRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

        Patient entity = Patient.builder()
                .patientId(dto.getPatientId())
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .build();

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
}
