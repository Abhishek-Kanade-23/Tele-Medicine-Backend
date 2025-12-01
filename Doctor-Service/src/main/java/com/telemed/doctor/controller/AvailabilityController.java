package com.telemed.doctor.controller;

import com.telemed.doctor.model.DoctorAvailability;
import com.telemed.doctor.model.dto.AvailabilityDTO;
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
    @PostMapping
    public DoctorAvailability addAvailability(@RequestBody AvailabilityDTO dto) {
        return availabilityService.addAvailability(dto);
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
