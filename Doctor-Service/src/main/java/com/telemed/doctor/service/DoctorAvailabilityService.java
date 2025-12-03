package com.telemed.doctor.service;

import com.telemed.doctor.exception.ResourceNotFoundException;
import com.telemed.doctor.model.Doctor;
import com.telemed.doctor.model.DoctorAvailability;
import com.telemed.doctor.model.dto.AvailabilityDTO;
import com.telemed.doctor.model.dto.DoctorAvailabilityResponseDTO;
import com.telemed.doctor.model.dto.WorkingHourDTO;
import com.telemed.doctor.repository.DoctorAvailabilityRepository;
import com.telemed.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Autowired
    private DoctorRepository doctorRepository ;

    @Transactional
    public DoctorAvailabilityResponseDTO addAvailability(AvailabilityDTO dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        System.out.println(
                "Received Doctor ==> " + doctor
        );

        dto.getWorkingHours().forEach((day, wh) -> {

            if (!wh.isEnabled()) {
                doctorAvailabilityRepository.deleteByDoctorIdAndDay(dto.getDoctorId(), day);
                return;
            }

            // enabled = true → insert/update
            DoctorAvailability hours =
                    doctorAvailabilityRepository.findByDoctorIdAndDay(dto.getDoctorId(), day)
                            .orElse(new DoctorAvailability());

            hours.setDoctorId(doctor.getDoctorId());
            hours.setDay(day);
            hours.setEnabled(true);
            hours.setStartTime(wh.getStart());
            hours.setEndTime(wh.getEnd());

            doctorAvailabilityRepository.save(hours);
        });


        // --- Build response DTO ---
        Map<String, WorkingHourDTO> responseWorkingHours = new HashMap<>();

        doctorAvailabilityRepository.findAllByDoctorId(dto.getDoctorId())
                .forEach(x -> {
                    WorkingHourDTO wh = new WorkingHourDTO();
                    wh.setEnabled(x.isEnabled());
                    wh.setStart(x.getStartTime());
                    wh.setEnd(x.getEndTime());
                    responseWorkingHours.put(x.getDay(), wh);
                });

        // Include disabled days (optional)
        dto.getWorkingHours().keySet().forEach(day -> {
            responseWorkingHours.putIfAbsent(day, dto.getWorkingHours().get(day));
        });

        DoctorAvailabilityResponseDTO res = new DoctorAvailabilityResponseDTO(
                dto.getDoctorId(),
                dto.getAppointmentDuration(),
                responseWorkingHours
        );

        System.out.println("Response ==> " + res);

        return res ;
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
