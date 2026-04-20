package com.petconnect.controller;

import com.petconnect.model.Pet;
import com.petconnect.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetApiController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<Pet>> getAll(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String breed) {
        if (type != null || status != null || breed != null)
            return ResponseEntity.ok(petService.filterPets(type, status, breed));
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getById(@PathVariable Long id) {
        return petService.getPetById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Pet>> search(@RequestParam String q) {
        return ResponseEntity.ok(petService.searchPets(q));
    }

    @PostMapping
    public ResponseEntity<Pet> create(@RequestBody Pet pet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.savePet(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> update(@PathVariable Long id, @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.updatePet(id, pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<Map<String,String>> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws Exception {
        String url = petService.saveImage(file, "uploads/pets/");
        Pet pet = petService.getPetById(id).orElseThrow();
        pet.setImageUrl(url);
        petService.savePet(pet);
        return ResponseEntity.ok(Map.of("imageUrl", url));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String,Long>> stats() {
        return ResponseEntity.ok(Map.of(
            "available", petService.countAvailable(),
            "adopted",   petService.countAdopted()
        ));
    }
}
