package com.telemed.doctor.service;

import com.telemed.doctor.client.VisitServiceClient;
import com.telemed.doctor.model.DoctorPatientMap;
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

    public List<PatientSummaryDTO> getPatientsForDoctor(Long doctorId) {

        List<DoctorPatientMap> mappings = doctorPatientMapRepository.findByDoctorId(doctorId);

        return mappings.stream().map(map -> {

            // Fetch last visit summary from Visit Service
            var lastVisit = visitServiceClient.getLastVisitSummary(map.getPatientId());

            return PatientSummaryDTO.builder()
                    .patientId(map.getPatientId())
                    .patientName("Patient Name Placeholder") // if needed fetch from Patient Service
                    .lastConsultationId(lastVisit.getLastConsultationId())
                    .lastConsultationTime(lastVisit.getLastConsultationTime())
                    .lastPrescriptionId(lastVisit.getLastPrescriptionId())
                    .build();

        }).collect(Collectors.toList());
    }
}
