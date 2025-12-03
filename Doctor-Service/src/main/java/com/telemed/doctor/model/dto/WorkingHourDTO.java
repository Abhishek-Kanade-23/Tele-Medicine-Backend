package com.telemed.doctor.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkingHourDTO {
    private String start;
    private String end;
    private boolean enabled;


}
