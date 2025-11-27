package com.project.VisitService.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitDetailsDTO {

    private Long visitId;

    private Long doctorId;
    private String doctorName;

    private Long patientId;
    private String patientName;   // ✓ ADDED
    private String reason;

    private LocalDateTime scheduledTime;
    private String status;

    private Long consultationId;
    private String notes;
    private LocalDateTime consultationTime;
    private String followUpDate;

    private Long prescriptionId;
}
