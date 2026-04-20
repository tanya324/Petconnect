package com.petconnect.service;

import com.petconnect.model.Pet;
import com.petconnect.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getAvailablePets() {
        return petRepository.findByStatus(Pet.PetStatus.AVAILABLE);
    }

    public List<Pet> getPetsByType(String type) {
        return petRepository.findByType(type);
    }

    public List<Pet> filterPets(String type, String status, String breed) {
        return petRepository.filterPets(
            (type != null && type.equalsIgnoreCase("All")) ? null : type,
            status,
            breed
        );
    }

    public List<Pet> searchPets(String query) {
        return petRepository.searchPets(query);
    }

    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public Pet savePet(Pet pet) {
        if (pet.getEnrolledDate() == null) {
            pet.setEnrolledDate(LocalDate.now());
        }
        return petRepository.save(pet);
    }

    public Pet updatePet(Long id, Pet updated) {
        Pet pet = petRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pet not found: " + id));
        pet.setName(updated.getName());
        pet.setType(updated.getType());
        pet.setBreed(updated.getBreed());
        pet.setAge(updated.getAge());
        pet.setHealthCondition(updated.getHealthCondition());
        pet.setVaccinationStatus(updated.getVaccinationStatus());
        pet.setDescription(updated.getDescription());
        pet.setStatus(updated.getStatus());
        return petRepository.save(pet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    public String saveImage(MultipartFile file, String uploadDir) throws IOException {
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) Files.createDirectories(dirPath);
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = dirPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/" + uploadDir + filename;
    }

    public long countAvailable() {
        return petRepository.countByStatus(Pet.PetStatus.AVAILABLE);
    }

    public long countAdopted() {
        return petRepository.countByStatus(Pet.PetStatus.ADOPTED);
    }
}
