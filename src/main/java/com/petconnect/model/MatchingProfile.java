package com.petconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matching_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String livingSpace;     // Apartment, House with Yard, Studio

    @Column
    private String lifestyle;       // Active, Calm, Balanced

    @Column
    private String allergies;       // None, Pet fur, Feathers

    @Column
    private String experience;      // First-time, Some, Experienced

    @Column
    private String budget;          // Under 500, 500-2000, 2000-5000, 5000+

    @Column(name = "preferred_type")
    private String preferredType;   // Dog, Cat, Any, etc.
}
