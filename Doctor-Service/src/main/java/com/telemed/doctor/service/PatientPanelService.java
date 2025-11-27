package com.telemed.doctor.service;

import com.telemed.doctor.client.PatientServiceClient;
import com.telemed.doctor.client.VisitServiceClient;
import com.telemed.doctor.model.DoctorPatientMap;
import com.telemed.doctor.model.dto.LastVisitSummaryDTO;
import com.telemed.doctor.model.dto.PatientSummaryDTO;
import com.telemed.doctor.repository.DoctorPatientMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientPanelService {

    private final DoctorPatientMapRepository doctorPatientMapRepository;
    private final VisitServiceClient visitServiceClient;
    private final PatientServiceClient patientServiceClient;

    public List<PatientSummaryDTO> getPatientsForDoctor(Long doctorId) {

        List<DoctorPatientMap> mappings = doctorPatientMapRepository.findByDoctorId(doctorId);

        return mappings.stream().map(map -> {

            // lastVisit *for this doctor & patient* (new)
            LastVisitSummaryDTO lastVisit = safeGetLastVisitForDoctorAndPatient(doctorId, map.getPatientId());

            // fetch patient name
            String patientName = safeGetPatientName(map.getPatientId());

            var builder = PatientSummaryDTO.builder().patientId(map.getPatientId()).patientName(patientName);

            if (lastVisit != null) {
                builder.lastConsultationId(lastVisit.getLastConsultationId())
                        .lastConsultationTime(lastVisit.getLastConsultationTime())
                        .lastPrescriptionId(lastVisit.getLastPrescriptionId());
            }

            return builder.build();

        }).collect(Collectors.toList());
    }

    private LastVisitSummaryDTO safeGetLastVisitForDoctorAndPatient(Long doctorId, Long patientId) {
        try {
            return visitServiceClient.getLastVisitSummaryForDoctorAndPatient(doctorId, patientId);
        } catch (Exception e) {
            System.out.println("⚠ PatientPanelService: failed to fetch last visit for patient " + patientId + " -> "
                    + e.getMessage());
            return null;
        }
    }

    private String safeGetPatientName(Long patientId) {
        try {
            return patientServiceClient.getPatientName(patientId);
        } catch (Exception e) {
            System.out.println(
                    "⚠ PatientPanelService: failed to fetch patient name for " + patientId + " -> " + e.getMessage());
            return "Unknown";
        }
    }
}
