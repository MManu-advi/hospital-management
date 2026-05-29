package com.hospital.hospital_management.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Admin Entity - maps to the 'admins' table in MySQL.
 * Represents a system administrator who manages doctors, patients, and appointments.
 */
@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // In production, always hash passwords (e.g., BCrypt)
}