package com.project.VisitService.controller;

import com.project.VisitService.dto.BookVisitRequest;
import com.project.VisitService.dto.BookVisitResponse;
import com.project.VisitService.dto.LastVisitSummaryDTO;
import com.project.VisitService.dto.VisitDetailsDTO;
import com.project.VisitService.dto.VisitHistoryDTO;
import com.project.VisitService.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    // 1) Book Visit -> POST /api/visits
    @PostMapping("/visits")
    public ResponseEntity<BookVisitResponse> bookVisit(@RequestBody BookVisitRequest request) {
        return ResponseEntity.ok(visitService.bookVisit(request));
    }

    // 2) Get Doctor Upcoming -> GET /api/doctors/{doctorId}/visits/upcoming
    @GetMapping("/doctors/{doctorId}/visits/upcoming")
    public ResponseEntity<List<VisitHistoryDTO>> getDoctorUpcoming(@PathVariable Long doctorId) {
        return ResponseEntity.ok(visitService.getUpcomingVisitsForDoctor(doctorId));
    }

    // 3) Update Visit Status -> PUT /api/visits/{visitId}/status
    @PutMapping("/visits/{visitId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long visitId, @RequestParam String status) {
        visitService.updateVisitStatus(visitId, status);
        return ResponseEntity.ok().build();
    }

    // 4) Patient Upcoming Visits -> GET /api/patients/{patientId}/visits/upcoming
    @GetMapping("/patients/{patientId}/visits/upcoming")
    public ResponseEntity<List<VisitHistoryDTO>> getUpcomingForPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(visitService.getUpcomingVisitsForPatient(patientId));
    }

    // 5) Patient Visit History -> GET /api/patients/{patientId}/visits/history
    @GetMapping("/patients/{patientId}/visits/history")
    public ResponseEntity<List<VisitHistoryDTO>> getPatientHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(visitService.getVisitHistoryForPatient(patientId));
    }

    // 6) Last visit summary -> GET /api/patients/{patientId}/visits/last
    @GetMapping("/patients/{patientId}/visits/last")
    public ResponseEntity<LastVisitSummaryDTO> getLastVisit(@PathVariable Long patientId) {
        return ResponseEntity.ok(visitService.getLastVisitSummaryForPatient(patientId));
    }

    // 7) Visit details -> GET /api/visits/{visitId}
    @GetMapping("/visits/{visitId}")
    public ResponseEntity<VisitDetailsDTO> getVisitDetails(@PathVariable Long visitId) {
        return ResponseEntity.ok(visitService.getVisitDetails(visitId));
    }

    // 8) Last visit for doctor & patient -> GET /api/doctors/{doctorId}/patients/{patientId}/visits/last
    @GetMapping("/doctors/{doctorId}/patients/{patientId}/visits/last")
    public ResponseEntity<LastVisitSummaryDTO> getLastVisitForDoctorAndPatient(
            @PathVariable Long doctorId, @PathVariable Long patientId) {
        return ResponseEntity.ok(visitService.getLastVisitSummaryForDoctorAndPatient(doctorId, patientId));
    }
}
