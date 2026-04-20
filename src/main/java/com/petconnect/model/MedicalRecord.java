package com.petconnect.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(nullable = false)
    private String condition;

    @Column(columnDefinition = "TEXT")
    private String treatment;

    @Column(name = "vet_name")
    private String vetName;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @Column(name = "next_visit")
    private LocalDate nextVisit;

    @Column
    private String notes;
}
