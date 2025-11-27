package com.telemed.doctor.model.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VisitSummaryDTO {

    private Long visitId;
    private Long patientId;
    private String patientName;
    private LocalDateTime scheduledTime;
    private String status;
    private Long doctorId;
    private String doctorName;
    private String reason;
    private Long prescriptionId;
    private Long consultationId;
}
