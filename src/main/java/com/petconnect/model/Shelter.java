package com.petconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shelters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String city;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private int capacity;

    @Column(name = "current_count")
    private int currentCount;
}
