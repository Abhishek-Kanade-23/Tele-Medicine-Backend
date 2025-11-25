package com.telemed.doctor.controller;

import com.telemed.doctor.model.dto.DashboardResponseDTO;
import com.telemed.doctor.service.DoctorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorDashboardController {

    private final DoctorDashboardService dashboardService;

    @GetMapping("/{doctorId}/dashboard")
    public DashboardResponseDTO getDashboard(@PathVariable Long doctorId) {
        return dashboardService.getDashboard(doctorId);
    }
}
