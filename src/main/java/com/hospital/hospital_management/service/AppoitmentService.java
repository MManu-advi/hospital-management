package com.hospital.hospital_management.service;

import com.hospital.hospital_management.dto.AppointmentRequest;
import com.hospital.hospital_management.entity.Appointment;
import com.hospital.hospital_management.entity.Doctor;
import com.hospital.hospital_management.entity.Patient;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.AppointmentRepository;
import com.hospital.hospital_management.repository.DoctorRepository;
import com.hospital.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AppointmentService - handles booking, updating, cancelling appointments
 * and auto-generating queue token numbers.
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // ─────────────────────────────────────────────
    // CREATE - Book a new appointment
    // ─────────────────────────────────────────────
    public Appointment addAppointment(AppointmentRequest request) {
        // Validate that patient exists
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + request.getPatientId()));

        // Validate that doctor exists
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + request.getDoctorId()));

        // Auto-generate token number based on how many appointments the doctor already
        // has on that date (acts like a queue ticket)
        long existingAppointments = appointmentRepository
                .countByDoctorIdAndAppointmentDate(doctor.getId(), request.getAppointmentDate());
        int tokenNumber = (int) existingAppointments + 1;

        // Build the appointment entity
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(request.getAppointmentDate())
                .slotTime(request.getSlotTime())
                .tokenNumber(tokenNumber)
                .status(request.getStatus() != null ? request.getStatus() : "SCHEDULED")
                .build();

        return appointmentRepository.save(appointment);
    }

    // ─────────────────────────────────────────────
    // READ ALL
    // ─────────────────────────────────────────────
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // ─────────────────────────────────────────────
    // READ BY ID
    // ─────────────────────────────────────────────
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + id));
    }

    // ─────────────────────────────────────────────
    // UPDATE - Update appointment status or time
    // ─────────────────────────────────────────────
    public Appointment updateAppointment(Long id, AppointmentRequest request) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + id));

        // Update date, time, and status if provided
        if (request.getAppointmentDate() != null) {
            existing.setAppointmentDate(request.getAppointmentDate());
        }
        if (request.getSlotTime() != null) {
            existing.setSlotTime(request.getSlotTime());
        }
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }

        // Update doctor if changed
        if (request.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Doctor not found with id: " + request.getDoctorId()));
            existing.setDoctor(doctor);
        }

        return appointmentRepository.save(existing);
    }

    // ─────────────────────────────────────────────
    // DELETE - Cancel an appointment
    // ─────────────────────────────────────────────
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + id));
        appointmentRepository.delete(appointment);
    }

    // ─────────────────────────────────────────────
    // EXTRA - Get appointments by patient
    // ─────────────────────────────────────────────
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // ─────────────────────────────────────────────
    // EXTRA - Get appointments by doctor
    // ─────────────────────────────────────────────
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
}