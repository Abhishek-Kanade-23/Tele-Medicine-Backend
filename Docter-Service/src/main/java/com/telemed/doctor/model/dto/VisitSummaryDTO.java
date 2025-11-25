package com.telemed.doctor.model.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitSummaryDTO {

    private Long visitId;
    private Long patientId;
    private String patientName;
    private LocalDateTime scheduledTime;
    private String status;
}
