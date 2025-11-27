package com.project.VisitService.controller;

import com.project.VisitService.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    // Create prescription for visit — returns prescriptionId
    @PostMapping("/{visitId}")
    public ResponseEntity<PrescriptionResponse> createPrescription(@PathVariable Long visitId) {
        Long id = prescriptionService.createPrescriptionForVisit(visitId);
        return ResponseEntity.ok(new PrescriptionResponse(id));
    }

    public record PrescriptionResponse(Long prescriptionId) {}
}
