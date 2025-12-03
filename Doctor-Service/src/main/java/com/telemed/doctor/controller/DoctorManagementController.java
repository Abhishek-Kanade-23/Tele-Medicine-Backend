package com.telemed.doctor.controller;

import com.telemed.doctor.model.dto.DoctorCreateDTO;
import com.telemed.doctor.model.dto.DoctorUpdateDTO;
import com.telemed.doctor.model.dto.DoctorResponseDTO;
import com.telemed.doctor.service.DoctorManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor/manage")
@RequiredArgsConstructor
public class DoctorManagementController {

    private final DoctorManagementService doctorService;

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorCreateDTO dto) {

        System.out.println("Received Req ==> " +  dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.createDoctor(dto));
    }

    // READ ONE
    @GetMapping("/{doctorId}")
    public DoctorResponseDTO getDoctor(@PathVariable Long doctorId) {
        return doctorService.getDoctor(doctorId);
    }

    // READ ALL
    @GetMapping("/all")
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // UPDATE
    @PutMapping("/{doctorId}")
    public DoctorResponseDTO updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody DoctorUpdateDTO dto
    ) {
        return doctorService.updateDoctor(doctorId, dto);
    }

    // DELETE
    @DeleteMapping("/{doctorId}")
    public String deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return "Doctor deleted successfully";
    }

    @GetMapping("/check/{doctorId}")
public DoctorResponseDTO checkDoctorExists(@PathVariable Long doctorId) {

    return doctorService.checkDoctorExists(doctorId);
}

}
