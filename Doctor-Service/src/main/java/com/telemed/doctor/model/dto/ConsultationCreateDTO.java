package com.telemed.doctor.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationCreateDTO {

    private String notes;
    private String followUpDate;  
}
