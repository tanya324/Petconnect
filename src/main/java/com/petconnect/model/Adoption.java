package com.petconnect.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "adoptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "adoption_date")
    private LocalDate adoptionDate;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status = AdoptionStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public enum AdoptionStatus {
        PENDING, APPROVED, REJECTED, COMPLETED
    }
}
