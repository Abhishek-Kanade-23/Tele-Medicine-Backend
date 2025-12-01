package com.project.VisitService.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LastVisitSummaryDTO {
    private Long lastVisitId;
    private Long lastConsultationId;
    private LocalDateTime lastConsultationTime;
    private Long lastPrescriptionId;
    private String videoLink;

}
