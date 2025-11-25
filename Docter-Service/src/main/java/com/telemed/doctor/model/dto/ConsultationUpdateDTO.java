package com.telemed.doctor.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationUpdateDTO {

    private Long consultationId;
    private String updatedNotes;
    private String followUpDate;
}
