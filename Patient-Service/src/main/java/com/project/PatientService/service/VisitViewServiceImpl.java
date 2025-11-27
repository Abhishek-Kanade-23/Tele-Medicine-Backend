package com.project.PatientService.service;

import com.project.PatientService.client.VisitServiceClient;
import com.project.PatientService.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitViewServiceImpl implements VisitViewService {

    private final VisitServiceClient visitServiceClient;

    @Override
    public Object bookVisit(BookVisitDTO dto) {
        return visitServiceClient.bookVisit(dto);
    }

    @Override
    public List<VisitHistoryDTO> getUpcomingVisits(Long patientId) {
        return visitServiceClient.getUpcomingVisits(patientId);
    }

    @Override
    public List<VisitHistoryDTO> getVisitHistory(Long patientId) {
        return visitServiceClient.getVisitHistory(patientId);
    }

    @Override
    public VisitDetailsDTO getVisitDetails(Long visitId) {
        return visitServiceClient.getVisitDetails(visitId);
    }
}
