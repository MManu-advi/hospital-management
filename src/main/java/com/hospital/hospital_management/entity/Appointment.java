package com.hospital.hospital_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private LocalDate appointmentDate;

    private LocalTime slotTime;

    private Integer tokenNumber;

    private String status;

    /**
     * Many appointments can belong to one patient.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"appointments"})
    @ToString.Exclude
    private Patient patient;

    /**
     * Many appointments can belong to one doctor.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonIgnoreProperties({"appointments"})
    @ToString.Exclude
    private Doctor doctor;
}