package com.telemed.doctor.service;

import com.telemed.doctor.client.VisitServiceClient;
import com.telemed.doctor.model.dto.DashboardResponseDTO;
import com.telemed.doctor.model.dto.VisitSummaryDTO;
import com.telemed.doctor.repository.DoctorPatientMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorDashboardService {

    private final DoctorPatientMapRepository doctorPatientMapRepository;
    private final VisitServiceClient visitServiceClient;

    /**
     * Public API used by controller - this method intentionally does DB reads first (short transaction)
     * and performs remote calls afterwards outside of that transaction. Remote-call failures are
     * caught so they don't mark a DB transaction rollback-only.
     */
    public DashboardResponseDTO getDashboard(Long doctorId) {

        // 1) Short read-only DB access to fetch counts/mappings
        int totalPatients = getTotalPatientsForDoctor(doctorId);

        // 2) Remote call (Visit-Service) — do outside DB tx and guard against failures
        List<VisitSummaryDTO> upcomingVisits;
        try {
            upcomingVisits = visitServiceClient.getUpcomingVisitsForDoctor(doctorId);
            if (upcomingVisits == null) upcomingVisits = Collections.emptyList();
        } catch (Exception e) {
            // Log and fall back to empty list
            System.out.println("⚠ DoctorDashboardService: failed to fetch upcoming visits -> " + e.getMessage());
            upcomingVisits = Collections.emptyList();
        }

        int appointmentsToday = (int) upcomingVisits.stream()
                .filter(v -> v.getScheduledTime() != null && v.getScheduledTime().toLocalDate().equals(java.time.LocalDate.now()))
                .count();

        return DashboardResponseDTO.builder()
                .doctorId(doctorId)
                .totalPatients(totalPatients)
                .appointmentsToday(appointmentsToday)
                .upcomingVisits(upcomingVisits)
                .build();
    }

    @Transactional(readOnly = true)
    protected int getTotalPatientsForDoctor(Long doctorId) {
        return doctorPatientMapRepository.findByDoctorId(doctorId).size();
    }
}
