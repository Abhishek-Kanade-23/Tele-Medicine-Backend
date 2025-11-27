package com.telemed.doctor.service;

import com.telemed.doctor.exception.ResourceNotFoundException;
import com.telemed.doctor.model.Doctor;
import com.telemed.doctor.model.dto.DoctorCreateDTO;
import com.telemed.doctor.model.dto.DoctorUpdateDTO;
import com.telemed.doctor.model.dto.DoctorResponseDTO;
import com.telemed.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorManagementService {

    private final DoctorRepository doctorRepository;

    // CREATE
    public DoctorResponseDTO createDoctor(DoctorCreateDTO dto) {

        if (doctorRepository.existsByDoctorId(dto.getDoctorId())) {
            throw new RuntimeException("Doctor with ID already exists.");
        }

        Doctor doctor = Doctor.builder()
                .doctorId(dto.getDoctorId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .specialization(dto.getSpecialization())
                .department(dto.getDepartment())
                .experience(dto.getExperience())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();

        Doctor saved = doctorRepository.save(doctor);

        return convertToResponse(saved);
    }

    // READ BY ID
    public DoctorResponseDTO getDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        return convertToResponse(doctor);
    }

    // READ ALL
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE
    public DoctorResponseDTO updateDoctor(Long doctorId, DoctorUpdateDTO dto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setDepartment(dto.getDepartment());
        doctor.setExperience(dto.getExperience());
        doctor.setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());

        Doctor updated = doctorRepository.save(doctor);

        return convertToResponse(updated);
    }

    // DELETE
    public void deleteDoctor(Long doctorId) {
        if (!doctorRepository.existsByDoctorId(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found");
        }
        doctorRepository.deleteById(doctorId);
    }

    // Helper mapper
    private DoctorResponseDTO convertToResponse(Doctor doctor) {
        return DoctorResponseDTO.builder()
                .doctorId(doctor.getDoctorId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .specialization(doctor.getSpecialization())
                .department(doctor.getDepartment())
                .experience(doctor.getExperience())
                .email(doctor.getEmail())
                .phone(doctor.getPhone())
                .build();
    }
}
