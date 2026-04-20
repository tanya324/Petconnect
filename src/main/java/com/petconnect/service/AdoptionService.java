package com.petconnect.service;

import com.petconnect.model.*;
import com.petconnect.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionService {
    private final AdoptionRepository adoptionRepository;
    private final PetRepository      petRepository;

    public Adoption requestAdoption(Pet pet, User user) {
        pet.setStatus(Pet.PetStatus.PENDING);
        petRepository.save(pet);
        Adoption a = Adoption.builder()
            .pet(pet).user(user)
            .adoptionDate(LocalDate.now())
            .status(Adoption.AdoptionStatus.PENDING)
            .build();
        return adoptionRepository.save(a);
    }

    public Adoption approve(Long id) {
        Adoption a = adoptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Adoption not found"));
        a.setStatus(Adoption.AdoptionStatus.APPROVED);
        a.getPet().setStatus(Pet.PetStatus.ADOPTED);
        petRepository.save(a.getPet());
        return adoptionRepository.save(a);
    }

    public List<Adoption> getByUser(Long userId)   { return adoptionRepository.findByUserId(userId); }
    public List<Adoption> getAll()                 { return adoptionRepository.findAll(); }
    public List<Adoption> getPending()             { return adoptionRepository.findByStatus(Adoption.AdoptionStatus.PENDING); }
    public long           countAll()               { return adoptionRepository.count(); }
}
