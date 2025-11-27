package com.telemed.doctor.service;

import com.telemed.doctor.model.DoctorPatientMap;
import com.telemed.doctor.repository.DoctorPatientMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorPatientMapService {

    private final DoctorPatientMapRepository doctorPatientMapRepository;

    public void createOrUpdateMapping(Long doctorId, Long patientId) {

        boolean exists = doctorPatientMapRepository
                .existsByDoctorIdAndPatientId(doctorId, patientId);

        if (exists) return; // Already mapped → skip

        DoctorPatientMap map = DoctorPatientMap.builder()
                .doctorId(doctorId)
                .patientId(patientId)
                .build();

        doctorPatientMapRepository.save(map);
    }
}
