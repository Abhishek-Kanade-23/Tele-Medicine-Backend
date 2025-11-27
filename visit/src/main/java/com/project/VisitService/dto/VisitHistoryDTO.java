package com.project.VisitService.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitHistoryDTO {

    private Long visitId;

    private Long doctorId;
    private Long patientId;     // ⭐ ADD
    private String patientName; // ⭐ ADD
    private String reason;

    private String doctorName;
    private LocalDateTime scheduledTime;
    private String status;

    private Long consultationId;
    private Long prescriptionId;
}
