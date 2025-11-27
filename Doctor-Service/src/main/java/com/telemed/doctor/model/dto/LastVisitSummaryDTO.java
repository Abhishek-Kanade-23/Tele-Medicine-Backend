package com.telemed.doctor.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

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
}
