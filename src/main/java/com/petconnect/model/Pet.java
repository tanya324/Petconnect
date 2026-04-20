package com.petconnect.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Pet name is required")
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String type;          // Dog, Cat, Rabbit, Bird, Fish, Hamster, Turtle

    @Column
    private String breed;

    @Column
    private String age;           // "2 years", "6 months"

    @Column
    private String gender;        // Male / Female

    @Column(name = "health_condition")
    private String healthCondition; // Excellent, Good, Needs Treatment

    @Column(name = "vaccination_status")
    private String vaccinationStatus; // Vaccinated, Partial, Not Vaccinated

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetStatus status = PetStatus.AVAILABLE;

    @Column(name = "enrolled_date")
    private LocalDate enrolledDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public enum PetStatus {
        AVAILABLE, ADOPTED, PENDING, UNDER_TREATMENT
    }
}
