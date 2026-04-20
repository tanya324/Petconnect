package com.petconnect.config;

import com.petconnect.model.*;
import com.petconnect.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository    userRepo;
    private final PetRepository     petRepo;
    private final ShelterRepository shelterRepo;
    private final PasswordEncoder   passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepo.count() > 0) return; // already seeded

        // Shelters
        Shelter s1 = shelterRepo.save(Shelter.builder()
            .name("Delhi Animal Welfare Society").city("Delhi")
            .address("Connaught Place, New Delhi").phone("011-23456789")
            .email("daws@shelter.in").capacity(80).currentCount(42).build());
        Shelter s2 = shelterRepo.save(Shelter.builder()
            .name("Mumbai SPCA").city("Mumbai")
            .address("Parel, Mumbai").phone("022-98765432")
            .email("spca@mumbai.in").capacity(100).currentCount(67).build());

        // Admin user
        userRepo.save(User.builder()
            .name("Admin PetConnect").email("admin@petconnect.com")
            .password(passwordEncoder.encode("admin123"))
            .phone("9876543210").role(User.Role.ADMIN).active(true).build());

        // Regular user
        userRepo.save(User.builder()
            .name("Arjun Sharma").email("arjun@example.com")
            .password(passwordEncoder.encode("password"))
            .phone("9123456789").role(User.Role.USER).active(true).build());

        // Pets
        List<Pet> pets = List.of(
            Pet.builder().name("Max").type("Dog").breed("Golden Retriever").age("3 years")
                .gender("Male").healthCondition("Excellent").vaccinationStatus("Vaccinated")
                .description("Loves fetch and cuddles. Great with kids and other pets!")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(30))
                .shelter(s1).build(),
            Pet.builder().name("Luna").type("Cat").breed("Persian").age("2 years")
                .gender("Female").healthCondition("Good").vaccinationStatus("Vaccinated")
                .description("Calm and affectionate. Loves window watching and gentle play.")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(20))
                .shelter(s1).build(),
            Pet.builder().name("Coco").type("Rabbit").breed("Holland Lop").age("1 year")
                .gender("Female").healthCondition("Excellent").vaccinationStatus("Vaccinated")
                .description("Energetic and curious. Great apartment pet!")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(15))
                .shelter(s2).build(),
            Pet.builder().name("Tweetie").type("Bird").breed("African Grey Parrot").age("5 years")
                .gender("Male").healthCondition("Good").vaccinationStatus("Vaccinated")
                .description("Talks and mimics! Very intelligent and social.")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(10))
                .shelter(s1).build(),
            Pet.builder().name("Bubbles").type("Fish").breed("Betta Fish").age("6 months")
                .gender("Male").healthCondition("Excellent").vaccinationStatus("N/A")
                .description("Beautiful fins. Low-maintenance and peaceful.")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(5))
                .shelter(s2).build(),
            Pet.builder().name("Hammy").type("Hamster").breed("Syrian Hamster").age("8 months")
                .gender("Male").healthCondition("Good").vaccinationStatus("N/A")
                .description("Tiny and adorable. Perfect for small spaces!")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(8))
                .shelter(s1).build(),
            Pet.builder().name("Sheldon").type("Turtle").breed("Red-Eared Slider").age("4 years")
                .gender("Male").healthCondition("Good").vaccinationStatus("N/A")
                .description("Slow and steady! Very long-lived companion.")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(45))
                .shelter(s2).build(),
            Pet.builder().name("Bruno").type("Dog").breed("Labrador").age("1 year")
                .gender("Male").healthCondition("Excellent").vaccinationStatus("Vaccinated")
                .description("Super playful and energetic. Loves outdoor runs!")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(12))
                .shelter(s2).build(),
            Pet.builder().name("Kitty").type("Cat").breed("British Shorthair").age("4 years")
                .gender("Female").healthCondition("Good").vaccinationStatus("Vaccinated")
                .description("Laid back and independent. Quiet apartment cat.")
                .status(Pet.PetStatus.ADOPTED).enrolledDate(LocalDate.now().minusDays(60))
                .shelter(s1).build(),
            Pet.builder().name("Goldie").type("Fish").breed("Goldfish").age("1 year")
                .gender("Female").healthCondition("Excellent").vaccinationStatus("N/A")
                .description("Classic and cheerful. Easy to care for!")
                .status(Pet.PetStatus.AVAILABLE).enrolledDate(LocalDate.now().minusDays(3))
                .shelter(s2).build()
        );
        petRepo.saveAll(pets);
        System.out.println("✅ PetConnect data seeded successfully!");
    }
}
