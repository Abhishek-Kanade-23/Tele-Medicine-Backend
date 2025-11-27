package com.project.VisitService.service;

import com.project.VisitService.dto.*;
//import com.project.VisitService.model.Visit;

import java.util.List;

public interface VisitService {

    BookVisitResponse bookVisit(BookVisitRequest request);

    List<VisitHistoryDTO> getUpcomingVisitsForDoctor(Long doctorId);

    void updateVisitStatus(Long visitId, String status);

    List<VisitHistoryDTO> getVisitHistoryForPatient(Long patientId);

    VisitDetailsDTO getVisitDetails(Long visitId);

    LastVisitSummaryDTO getLastVisitSummaryForPatient(Long patientId);
    public List<VisitHistoryDTO> getUpcomingVisitsForPatient(Long patientId);
    
    LastVisitSummaryDTO getLastVisitSummaryForDoctorAndPatient(Long doctorId, Long patientId);

}
