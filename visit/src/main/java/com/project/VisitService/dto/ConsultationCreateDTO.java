package com.project.VisitService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationCreateDTO {
    private String notes;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate followUpDate;
}
