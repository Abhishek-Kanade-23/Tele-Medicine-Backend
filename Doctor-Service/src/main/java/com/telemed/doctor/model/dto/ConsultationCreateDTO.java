package com.telemed.doctor.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationCreateDTO {
    private String notes;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate followUpDate;
}

