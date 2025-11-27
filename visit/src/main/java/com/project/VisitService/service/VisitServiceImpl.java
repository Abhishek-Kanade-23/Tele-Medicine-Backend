package com.project.VisitService.service;

import com.project.VisitService.client.DoctorClient;
import com.project.VisitService.client.PatientClient;
import com.project.VisitService.dto.*;
import com.project.VisitService.model.Consultation;
import com.project.VisitService.model.Prescription;
import com.project.VisitService.model.Visit;
import com.project.VisitService.repository.ConsultationRepository;
import com.project.VisitService.repository.PrescriptionRepository;
import com.project.VisitService.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final ConsultationRepository consultationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DoctorClient doctorClient;
    private final PatientClient patientClient;

    // ---------------------------
    // BOOK VISIT
    // ---------------------------
    @Override
    @Transactional
    public BookVisitResponse bookVisit(BookVisitRequest request) {

        // 1) Validate doctor exists
        var doctor = doctorClient.getDoctor(request.getDoctorId());
        if (doctor == null) {
            throw new RuntimeException("Doctor not found: " + request.getDoctorId());
        }

        // 2) Validate patient exists
        var patientDto = patientClient.getPatient(request.getPatientId());
        if (patientDto == null) {
            throw new RuntimeException("Patient not found: " + request.getPatientId());
        }

        // 3) Build visit and persist (including reason)
        Visit visit = Visit.builder()
                .doctorId(request.getDoctorId())
                .patientId(request.getPatientId())
                .scheduledTime(request.getScheduledTime())
                .status(Visit.VisitStatus.SCHEDULED)
                .reason(request.getReason())
                .build();

        Visit saved = visitRepository.save(visit);

        // 4) Best-effort: create mapping doctor <-> patient
//        try {
//            doctorClient.createMapping(request.getDoctorId(), request.getPatientId());
//        } catch (Exception ignored) {}

        return BookVisitResponse.builder()
                .visitId(saved.getVisitId())
                .message("Visit booked successfully")
                .build();
    }


    // ---------------------------
    // Get upcoming visits for doctor
    // ---------------------------
    @Override
    public List<VisitHistoryDTO> getUpcomingVisitsForDoctor(Long doctorId) {

        var visits = visitRepository.findByDoctorIdAndStatusOrderByScheduledTimeAsc(
                doctorId, Visit.VisitStatus.SCHEDULED
        );

        return visits.stream().map(v -> {
            VisitHistoryDTO.VisitHistoryDTOBuilder builder = VisitHistoryDTO.builder()
                    .visitId(v.getVisitId())
                    .doctorId(v.getDoctorId())
                    .patientId(v.getPatientId())
                    .scheduledTime(v.getScheduledTime())
                    .status(v.getStatus().name())
                    .reason(v.getReason());

            // doctor name (non-blocking)
            try {
                builder.doctorName(doctorClient.getDoctorName(v.getDoctorId()));
            } catch (Exception ignored) {}

            // patient name
            try {
                builder.patientName(patientClient.getPatientName(v.getPatientId()));
            } catch (Exception ignored) {
                builder.patientName("Unknown");
            }

            return builder.build();
        }).collect(Collectors.toList());
    }

    // ---------------------------
    // Update visit status
    // ---------------------------
    @Override
    @Transactional
    public void updateVisitStatus(Long visitId, String status) {
        var visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        visit.setStatus(Visit.VisitStatus.valueOf(status.toUpperCase()));
        visitRepository.save(visit);
    }

    // ---------------------------
    // Visit history for patient
    // ---------------------------
    @Override
    public List<VisitHistoryDTO> getVisitHistoryForPatient(Long patientId) {
        var visits = visitRepository.findByPatientIdOrderByScheduledTimeDesc(patientId);

        return visits.stream().map(v -> {
            VisitHistoryDTO.VisitHistoryDTOBuilder builder = VisitHistoryDTO.builder()
                    .visitId(v.getVisitId())
                    .doctorId(v.getDoctorId())
                    .patientId(v.getPatientId())
                    .status(v.getStatus().name())
                    .scheduledTime(v.getScheduledTime())
                    .reason(v.getReason())
                    .consultationId(
                            consultationRepository.findByVisitId(v.getVisitId())
                                    .map(Consultation::getConsultationId).orElse(null)
                    )
                    .prescriptionId(
                            prescriptionRepository.findTopByVisitIdOrderByCreatedAtDesc(v.getVisitId())
                                    .map(Prescription::getPrescriptionId).orElse(null)
                    );

            try {
                builder.doctorName(doctorClient.getDoctorName(v.getDoctorId()));
            } catch (Exception ignored) {}

            try {
                builder.patientName(patientClient.getPatientName(v.getPatientId()));
            } catch (Exception ignored) {}

            return builder.build();
        }).collect(Collectors.toList());
    }

    // ---------------------------
    // Efficient upcoming visits for patient
    // ---------------------------
    @Override
    public List<VisitHistoryDTO> getUpcomingVisitsForPatient(Long patientId) {
        var visits = visitRepository.findByPatientIdAndStatusOrderByScheduledTimeAsc(patientId, Visit.VisitStatus.SCHEDULED);

        return visits.stream().map(v -> VisitHistoryDTO.builder()
                .visitId(v.getVisitId())
                .doctorId(v.getDoctorId())
                .patientId(v.getPatientId())
                .doctorName(doctorClient.getDoctorName(v.getDoctorId()))
                .patientName(patientClient.getPatientName(v.getPatientId()))
                .scheduledTime(v.getScheduledTime())
                .status(v.getStatus().name())
                .reason(v.getReason())
                .build()
        ).collect(Collectors.toList());
    }

    // ---------------------------
    // Visit details
    // ---------------------------
    @Override
    public VisitDetailsDTO getVisitDetails(Long visitId) {
        var visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        var cons = consultationRepository.findByVisitId(visitId);
        var pres = prescriptionRepository.findTopByVisitIdOrderByCreatedAtDesc(visitId);

        VisitDetailsDTO dto = VisitDetailsDTO.builder()
                .visitId(visit.getVisitId())
                .doctorId(visit.getDoctorId())
                .patientId(visit.getPatientId())
                .scheduledTime(visit.getScheduledTime())
                .status(visit.getStatus().name())
                .reason(visit.getReason())
                .build();

        try {
            dto.setDoctorName(doctorClient.getDoctorName(visit.getDoctorId()));
        } catch (Exception ignored) {}

        try {
            dto.setPatientName(patientClient.getPatientName(visit.getPatientId()));
        } catch (Exception ignored) {
            dto.setPatientName("Unknown");
        }

        cons.ifPresent(c -> {
            dto.setConsultationId(c.getConsultationId());
            dto.setNotes(c.getNotes());
            dto.setConsultationTime(c.getCreatedAt());
            dto.setFollowUpDate(c.getFollowUpDate() == null ? null : c.getFollowUpDate().toString());
        });

        pres.ifPresent(p -> dto.setPrescriptionId(p.getPrescriptionId()));

        return dto;
    }

    // ---------------------------
    // Last visit summary (patient)
    // ---------------------------
    @Override
    public LastVisitSummaryDTO getLastVisitSummaryForPatient(Long patientId) {
        var visits = visitRepository.findByPatientIdOrderByScheduledTimeDesc(patientId);

        if (visits.isEmpty()) return null;

        var last = visits.get(0);

        var cons = consultationRepository.findByVisitId(last.getVisitId());
        var pres = prescriptionRepository.findTopByVisitIdOrderByCreatedAtDesc(last.getVisitId());

        return LastVisitSummaryDTO.builder()
                .lastVisitId(last.getVisitId())
                .lastConsultationId(cons.map(Consultation::getConsultationId).orElse(null))
                .lastConsultationTime(cons.map(Consultation::getCreatedAt).orElse(null))
                .lastPrescriptionId(pres.map(Prescription::getPrescriptionId).orElse(null))
                .build();
    }

    // ---------------------------
    // Last visit summary for doctor & patient (new)
    // ---------------------------
    @Override
    public LastVisitSummaryDTO getLastVisitSummaryForDoctorAndPatient(Long doctorId, Long patientId) {
        var visitOpt = visitRepository.findTopByPatientIdAndDoctorIdOrderByScheduledTimeDesc(patientId, doctorId);
        if (visitOpt.isEmpty()) return null;

        var last = visitOpt.get();
        var cons = consultationRepository.findByVisitId(last.getVisitId());
        var pres = prescriptionRepository.findTopByVisitIdOrderByCreatedAtDesc(last.getVisitId());

        return LastVisitSummaryDTO.builder()
                .lastVisitId(last.getVisitId())
                .lastConsultationId(cons.map(Consultation::getConsultationId).orElse(null))
                .lastConsultationTime(cons.map(Consultation::getCreatedAt).orElse(null))
                .lastPrescriptionId(pres.map(Prescription::getPrescriptionId).orElse(null))
                .build();
    }
}
