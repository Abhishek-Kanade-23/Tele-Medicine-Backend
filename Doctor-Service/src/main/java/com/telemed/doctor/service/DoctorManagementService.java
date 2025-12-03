package com.telemed.doctor.service;

import com.telemed.doctor.exception.ResourceNotFoundException;
import com.telemed.doctor.model.Doctor;
import com.telemed.doctor.model.dto.DoctorCreateDTO;
import com.telemed.doctor.model.dto.DoctorUpdateDTO;
import com.telemed.doctor.model.dto.DoctorResponseDTO;
import com.telemed.doctor.model.enums.Gender;
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

//        if (doctorRepository.existsByDoctorId(dto.getDoctorId())) {
//            throw new RuntimeException("Doctor with ID already exists.");
//        }

        Doctor doctor = Doctor.builder()
                .doctorId(dto.getDoctorId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .specialization(dto.getSpecialization())
                .department(dto.getDepartment())
                .experience(dto.getExperience())
                .emailId(dto.getEmailId())
                .phoneNumber(dto.getPhoneNumber())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .isProfileComplete(dto.isProfileComplete())
                .build();

        Doctor saved = doctorRepository.save(doctor);

        System.out.println("Saved Doctor ==> " + saved);

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

        System.out.println("doctorId ==> " +doctor + " dto " + dto);
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setDepartment(dto.getDepartment());
        doctor.setExperience(dto.getExperience());
        doctor.setEmailId(dto.getEmailId());
        doctor.setPhoneNumber(dto.getPhoneNumber());
        doctor.setAddress(dto.getAddress());
        doctor.setGender(dto.getGender());


        Doctor updated = doctorRepository.save(doctor);


        System.out.println("Updated Docter ==> " + updated);

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
                .emailId(doctor.getEmailId())
                .phoneNumber(doctor.getPhoneNumber())
                .address(doctor.getAddress())
                .gender(doctor.getGender().toString())
                .isProfileComplete(doctor.isProfileComplete())
                .build();
    }

    public DoctorResponseDTO checkDoctorExists(Long doctorId) {


        System.out.println("Recevide req ==> " + doctorId);

    Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        System.out.println("Recevide Doctor ==> " + doctor);

    if (doctor == null) {
        // Return EMPTY PROFILE
        return DoctorResponseDTO.builder()
                .doctorId(null)
                .firstName("")
                .lastName("")
                .specialization(null)
                .department(null)
                .experience(0)
                .emailId("")
                .phoneNumber("")
                .gender("")
                .isProfileComplete(false)
                .build();
    }

    return convertToResponse(doctor);
}

}
