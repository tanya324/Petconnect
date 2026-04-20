package com.petconnect.service;

import com.petconnect.model.Appointment;
import com.petconnect.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public Appointment book(Appointment appt) { return appointmentRepository.save(appt); }

    public List<Appointment> getByPet(Long petId)   { return appointmentRepository.findByPetIdOrderByAppointmentDateAsc(petId); }
    public List<Appointment> getByUser(Long userId)  { return appointmentRepository.findByUserIdOrderByAppointmentDateAsc(userId); }
    public List<Appointment> getAll()               { return appointmentRepository.findAll(); }

    public Appointment complete(Long id) {
        Appointment a = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
        a.setStatus(Appointment.AppointmentStatus.COMPLETED);
        return appointmentRepository.save(a);
    }

    public void cancel(Long id) {
        Appointment a = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
        a.setStatus(Appointment.AppointmentStatus.CANCELLED);
        appointmentRepository.save(a);
    }
}
