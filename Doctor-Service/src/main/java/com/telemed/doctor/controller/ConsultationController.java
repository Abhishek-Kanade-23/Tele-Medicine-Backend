package com.telemed.doctor.controller;

import com.telemed.doctor.model.dto.ConsultationCreateDTO;
import com.telemed.doctor.model.dto.ConsultationResponseDTO;
import com.telemed.doctor.model.dto.ConsultationUpdateDTO;
import com.telemed.doctor.service.ConsultationNotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationNotesService consultationNotesService;

    // Write new notes
    @PostMapping("/{visitId}")
    public ConsultationResponseDTO createConsultationNotes(
            @PathVariable Long visitId,
            @RequestBody ConsultationCreateDTO dto
    ) {
        return consultationNotesService.createConsultationNotes(visitId, dto);
    }

    // Edit notes
    @PutMapping("/{consultationId}")
    public ConsultationResponseDTO updateConsultationNotes(
            @PathVariable Long consultationId,
            @RequestBody ConsultationUpdateDTO dto
    ) {
        return consultationNotesService.updateConsultationNotes(consultationId, dto);
    }
}
