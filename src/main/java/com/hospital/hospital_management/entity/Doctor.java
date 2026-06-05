package com.hospital.hospital_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Doctor Entity - maps to the 'doctors' table in MySQL.
 * Represents a doctor working in the hospital.
 */
@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String specialization;

    private Integer experience;

    private String availableTime;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    /**
     * One doctor can have many appointments.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
public Long getId() {
    return id;
}
}