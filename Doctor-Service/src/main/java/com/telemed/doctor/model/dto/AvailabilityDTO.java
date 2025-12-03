package com.telemed.doctor.model.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityDTO {

    private Long doctorId;
    private int appointmentDuration ;
    private Map<String, WorkingHourDTO> workingHours;
}
