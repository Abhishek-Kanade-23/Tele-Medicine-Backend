package com.telemed.doctor.controller;

import com.telemed.doctor.model.dto.PatientSummaryDTO;
import com.telemed.doctor.service.PatientPanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class PatientPanelController {

    private final PatientPanelService patientPanelService;

    @GetMapping("/{doctorId}/patients")
    public List<PatientSummaryDTO> getPatients(@PathVariable Long doctorId) {
        return patientPanelService.getPatientsForDoctor(doctorId);
    }
}
