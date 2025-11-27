package com.project.VisitService.controller;

import com.project.VisitService.dto.ConsultationCreateDTO;
import com.project.VisitService.dto.ConsultationUpdateDTO;
import com.project.VisitService.model.Consultation;
import com.project.VisitService.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    // Add Consultation
    @PostMapping("/{visitId}")
    public ResponseEntity<Consultation> addConsultation(
            @PathVariable Long visitId,
            @RequestBody ConsultationCreateDTO dto) {

        Consultation created = consultationService.addConsultation(
                visitId,
                dto.getNotes(),
                dto.getFollowUpDate()
        );
        return ResponseEntity.ok(created);
    }

    // Update Consultation
    @PutMapping("/{consultationId}")
    public ResponseEntity<Consultation> updateConsultation(
            @PathVariable Long consultationId,
            @RequestBody ConsultationUpdateDTO dto) {

        Consultation updated = consultationService.updateConsultation(
                consultationId,
                dto.getNotes(),
                dto.getFollowUpDate()
        );
        return ResponseEntity.ok(updated);
    }
}
