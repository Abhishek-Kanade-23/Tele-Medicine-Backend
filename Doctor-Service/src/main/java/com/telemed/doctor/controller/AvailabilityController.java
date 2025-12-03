package com.telemed.doctor.controller;

import com.telemed.doctor.model.DoctorAvailability;
import com.telemed.doctor.model.dto.AvailabilityDTO;
import com.telemed.doctor.model.dto.DoctorAvailabilityResponseDTO;
import com.telemed.doctor.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final DoctorAvailabilityService availabilityService;

    // Add availability slot
    @PutMapping
    public ResponseEntity<DoctorAvailabilityResponseDTO> addAvailability(@RequestBody AvailabilityDTO dto) {

        System.out.println("AvailabilityDTO ==> " + dto);
        return ResponseEntity.ok(availabilityService.addAvailability(dto));
//        return  ResponseEntity.ok(new DoctorAvailabilityResponseDTO()) ;
    }

    // Get all slots for doctor
    @GetMapping("/{doctorId}")
    public List<DoctorAvailability> getAvailability(@PathVariable Long doctorId) {
        return availabilityService.getAvailabilityForDoctor(doctorId);
    }

    // Delete slot
    @DeleteMapping("/{doctorId}/slot/{slotId}")
    public ResponseEntity<String> deleteSlot(
            @PathVariable Long doctorId,
            @PathVariable Long slotId
    ) {
        availabilityService.deleteSlot(doctorId, slotId);
        return ResponseEntity.ok("Availability slot removed");
    }
}
