package com.telemed.doctor.service;

import com.telemed.doctor.exception.ResourceNotFoundException;
import com.telemed.doctor.model.DoctorAvailability;
import com.telemed.doctor.model.dto.AvailabilityDTO;
import com.telemed.doctor.repository.DoctorAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    public DoctorAvailability addAvailability(AvailabilityDTO dto) {

        DoctorAvailability availability = DoctorAvailability.builder()
                .doctorId(dto.getDoctorId())
                .availableDate(dto.getAvailableDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        return doctorAvailabilityRepository.save(availability);
    }

    public List<DoctorAvailability> getAvailabilityForDoctor(Long doctorId) {
        return doctorAvailabilityRepository.findByDoctorId(doctorId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteSlot(Long doctorId, Long slotId) {
        DoctorAvailability slot = doctorAvailabilityRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

        if (!slot.getDoctorId().equals(doctorId)) {
            throw new ResourceNotFoundException("Slot does not belong to doctor");
        }

        doctorAvailabilityRepository.delete(slot);
    }
}
