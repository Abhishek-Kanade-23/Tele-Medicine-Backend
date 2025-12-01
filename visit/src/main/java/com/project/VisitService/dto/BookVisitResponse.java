package com.project.VisitService.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookVisitResponse {

    private Long visitId;
    private String message;   // Example: "Visit booked successfully"
    private String videoLink;

}
