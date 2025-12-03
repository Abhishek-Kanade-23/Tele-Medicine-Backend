package com.telemed.doctor.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAvailabilityResponseDTO {
    private Long doctorId;
    private int appointmentDuration;
    private Map<String, WorkingHourDTO> workingHours;
}
