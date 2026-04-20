package com.petconnect.controller;

import com.petconnect.model.MedicalRecord;
import com.petconnect.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medical")
@RequiredArgsConstructor
public class MedicalApiController {

    private final MedicalService medicalService;

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<MedicalRecord>> getByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(medicalService.getRecordsByPet(petId));
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> save(@RequestBody MedicalRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalService.save(record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
