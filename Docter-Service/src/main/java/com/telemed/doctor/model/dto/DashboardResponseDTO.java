package com.telemed.doctor.model.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {

    private Long doctorId;
    private int totalPatients;
    private int appointmentsToday;
    private List<VisitSummaryDTO> upcomingVisits;
}
