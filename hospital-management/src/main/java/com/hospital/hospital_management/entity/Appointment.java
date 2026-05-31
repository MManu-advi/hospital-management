package com.hospital.hospital_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Appointment Entity - maps to the 'appointments' table in MySQL.
 * Links patients and doctors; tracks scheduled appointments with token/queue info.
 */
@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate appointmentDate; // e.g., 2025-08-15

    private LocalTime slotTime; // e.g., 10:30 AM

    private Integer tokenNumber; // Queue token number assigned at booking

    private String status; // e.g., "SCHEDULED", "COMPLETED", "CANCELLED"

    /**
     * Many appointments can belong to one patient.
     * @ManyToOne creates a foreign key column 'patient_id' in the appointments table.
     * fetch = LAZY avoids loading patient data unless explicitly needed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false) // Foreign key column name
    @ToString.Exclude
    private Patient patient;

    /**
     * Many appointments can belong to one doctor.
     * Creates a foreign key column 'doctor_id' in the appointments table.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @ToString.Exclude
    private Doctor doctor;
}
