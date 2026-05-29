package com.hospital.hospital_management.repository;

import com.hospital.hospital_management.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * AppointmentRepository - handles all database operations for Appointment.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find all appointments for a specific patient
    List<Appointment> findByPatientId(Long patientId);

    // Find all appointments for a specific doctor
    List<Appointment> findByDoctorId(Long doctorId);

    // Find appointments by date (useful for daily scheduling)
    List<Appointment> findByAppointmentDate(LocalDate date);

    // Count appointments for a specific doctor on a specific date (for token generation)
    long countByDoctorIdAndAppointmentDate(Long doctorId, LocalDate date);
}