package com.telemed.doctor.service;

import com.telemed.doctor.client.VisitServiceClient;
import com.telemed.doctor.model.dto.DashboardResponseDTO;
import com.telemed.doctor.model.dto.VisitSummaryDTO;
import com.telemed.doctor.repository.DoctorPatientMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorDashboardService {

    private final DoctorPatientMapRepository doctorPatientMapRepository;
    private final VisitServiceClient visitServiceClient;

    public DashboardResponseDTO getDashboard(Long doctorId) {

        int totalPatients = doctorPatientMapRepository.findByDoctorId(doctorId).size();

        // Fetch upcoming visits from Visit Service
        List<VisitSummaryDTO> upcomingVisits =
                visitServiceClient.getUpcomingVisitsForDoctor(doctorId);

        int appointmentsToday = (int) upcomingVisits.stream()
                .filter(v -> v.getScheduledTime().toLocalDate().equals(java.time.LocalDate.now()))
                .count();

        return DashboardResponseDTO.builder()
                .doctorId(doctorId)
                .totalPatients(totalPatients)
                .appointmentsToday(appointmentsToday)
                .upcomingVisits(upcomingVisits)
                .build();
    }
}
