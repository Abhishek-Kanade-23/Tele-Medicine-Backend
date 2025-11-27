package com.project.PatientService.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AvailabilityDTO {
    private Long doctorId;
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
