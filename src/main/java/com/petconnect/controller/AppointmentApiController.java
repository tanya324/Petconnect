package com.petconnect.controller;

import com.petconnect.model.Appointment;
import com.petconnect.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentApiController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(appointmentService.getAll());
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Appointment>> getByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(appointmentService.getByPet(petId));
    }

    @PostMapping
    public ResponseEntity<Appointment> book(@RequestBody Appointment appt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.book(appt));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Appointment> complete(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.complete(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        appointmentService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
