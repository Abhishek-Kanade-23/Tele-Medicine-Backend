package com.telemed.doctor.model.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientSummaryDTO {

    private Long patientId;
    private String patientName;

    private LocalDateTime lastConsultationTime;
    private Long lastConsultationId;
    private Long lastPrescriptionId;
}
