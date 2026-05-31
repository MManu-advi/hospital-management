package com.hospital.hospital_management.repository;

import com.hospital.hospital_management.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DoctorRepository - handles all database operations for Doctor.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Find doctors by their specialization (e.g., "Cardiologist")
    List<Doctor> findBySpecialization(String specialization);

    // Find a doctor by email
    Optional<Doctor> findByEmail(String email);
}
