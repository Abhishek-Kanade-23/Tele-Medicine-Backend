package com.project.PatientService.DTO;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitHistoryDTO {

    private Long visitId;

    private Long doctorId;
    private String doctorName;     // populated by Visit Service or Doctor Service

    private LocalDateTime scheduledTime;
    private String status;

    private Long consultationId;
    private Long prescriptionId;
}