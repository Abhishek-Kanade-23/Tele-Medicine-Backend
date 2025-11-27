package com.project.PatientService.service;

import com.project.PatientService.DTO.*;

import java.util.List;

public interface VisitViewService {

    Object bookVisit(BookVisitDTO dto);

    List<VisitHistoryDTO> getUpcomingVisits(Long patientId);

    List<VisitHistoryDTO> getVisitHistory(Long patientId);

    VisitDetailsDTO getVisitDetails(Long visitId);
}
