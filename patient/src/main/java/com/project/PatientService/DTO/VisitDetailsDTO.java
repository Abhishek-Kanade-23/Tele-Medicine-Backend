package com.project.PatientService.DTO;

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

    private LocalDateTime scheduledTime;
    private String status;

    // consultation
    private Long consultationId;
    private String notes;
    private LocalDateTime consultationTime;
    private String followUpDate;

    // prescription (lightweight)
    private Long prescriptionId;
}
