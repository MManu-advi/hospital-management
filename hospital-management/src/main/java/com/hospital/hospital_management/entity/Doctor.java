package com.hospital.hospital_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Doctor Entity - maps to the 'doctors' table in MySQL.
 * Represents a doctor working in the hospital.
 */
@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String specialization; // e.g., "Cardiologist", "Dermatologist"

    private Integer experience; // Years of experience

    private String availableTime; // e.g., "9:00 AM - 1:00 PM"

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    /**
     * One doctor can have many appointments.
     * mappedBy = "doctor" refers to the 'doctor' field in Appointment entity.
     */
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Appointment> appointments;
}
