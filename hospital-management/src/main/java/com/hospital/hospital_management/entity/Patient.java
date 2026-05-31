package com.hospital.hospital_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Patient Entity - maps to the 'patients' table in MySQL.
 * Represents a patient registered in the hospital system.
 */
@Entity
@Table(name = "patients")
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-arg constructor (required by JPA)
@AllArgsConstructor     // Lombok: generates all-arg constructor
@Builder                // Lombok: enables builder pattern (e.g., Patient.builder().name("John").build())
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer age;

    private String gender; // e.g., "Male", "Female", "Other"

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String password; // Note: In production, always store hashed passwords!

    /**
     * One patient can have many appointments.
     * mappedBy = "patient" refers to the 'patient' field in Appointment entity.
     * cascade = ALL means if patient is deleted, all their appointments are deleted too.
     * fetch = LAZY means appointments are loaded only when accessed (better performance).
     */
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude   // Prevent infinite loop in toString (Patient -> Appointments -> Patient...)
    private List<Appointment> appointments;
}
