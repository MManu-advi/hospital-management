package com.hospital.hospital_management.repository;

import com.hospital.hospital_management.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * PatientRepository - handles all database operations for Patient.
 *
 * By extending JpaRepository<Patient, Long>, we get FREE built-in methods:
 *   - save(patient)       → INSERT or UPDATE
 *   - findById(id)        → SELECT by primary key
 *   - findAll()           → SELECT all
 *   - deleteById(id)      → DELETE by primary key
 *   - existsById(id)      → Check if record exists
 *   - count()             → COUNT all records
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Custom query method - Spring generates SQL automatically from method name:
    // SELECT * FROM patients WHERE email = ?
    Optional<Patient> findByEmail(String email);

    // SELECT * FROM patients WHERE phone = ?
    Optional<Patient> findByPhone(String phone);
}