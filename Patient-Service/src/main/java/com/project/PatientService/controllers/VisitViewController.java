package com.project.PatientService.controllers;

import com.project.PatientService.DTO.*;
import com.project.PatientService.service.VisitViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient/visits")
@RequiredArgsConstructor
public class VisitViewController {

    private final VisitViewService visitViewService;

    // Book a new visit (internally calls Visit Service)
    @PostMapping("/book")
    public ResponseEntity<Object> bookVisit(@RequestBody BookVisitDTO dto) {
        return ResponseEntity.ok(visitViewService.bookVisit(dto));
    }

    // Get upcoming visits for a patient
    @GetMapping("/{patientId}/upcoming")
    public ResponseEntity<List<VisitHistoryDTO>> getUpcomingVisits(@PathVariable Long patientId) {
        return ResponseEntity.ok(visitViewService.getUpcomingVisits(patientId));
    }

    // Full visit history
    @GetMapping("/{patientId}/history")
    public ResponseEntity<List<VisitHistoryDTO>> getVisitHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(visitViewService.getVisitHistory(patientId));
    }

    // Visit details page
    @GetMapping("/{visitId}/details")
    public ResponseEntity<VisitDetailsDTO> getVisitDetails(@PathVariable Long visitId) {
        return ResponseEntity.ok(visitViewService.getVisitDetails(visitId));
    }
}
