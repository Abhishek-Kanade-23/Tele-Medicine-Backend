package com.project.PatientService.DTO;

import lombok.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookVisitDTO {

    private Long patientId;
    private Long doctorId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime scheduledTime;

    // optional - can be null
    private String reason;
}