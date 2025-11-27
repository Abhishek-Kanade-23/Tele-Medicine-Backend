package com.telemed.doctor.model.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationResponseDTO {
    private Long consultationId;
    private Long visitId;
    private String notes;
    private LocalDate followUpDate;      // ✅ FIXED
    private LocalDateTime createdAt;     // ✅ FIXED
    private LocalDateTime updatedAt;     // ✅ FIXED
}
