package com.project.PatientService.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookVisitResponse {

    private Long visitId;
    private String message;   // Example: "Visit booked successfully"
}
