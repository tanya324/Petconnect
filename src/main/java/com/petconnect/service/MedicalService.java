package com.petconnect.service;

import com.petconnect.model.MedicalRecord;
import com.petconnect.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalService {
    private final MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getRecordsByPet(Long petId) {
        return medicalRecordRepository.findByPetIdOrderByRecordDateDesc(petId);
    }

    public MedicalRecord save(MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }

    public void delete(Long id) { medicalRecordRepository.deleteById(id); }
}
