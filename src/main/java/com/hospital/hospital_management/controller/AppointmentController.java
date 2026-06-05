package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.AppointmentRequest;
import com.hospital.hospital_management.entity.Appointment;
import com.hospital.hospital_management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AppointmentController - exposes REST API endpoints for Appointment operations.
 *
 * Base URL: /appointments
 *
 * Key design decision:
 *   - Input  → AppointmentRequest DTO  (patientId, doctorId, date, slotTime)
 *   - Output → Appointment Entity      (includes full patient & doctor objects)
 *   This keeps the request lean while the response is rich.
 */
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // ─────────────────────────────────────────────
    // POST /appointments
    // Book a new appointment
    // ─────────────────────────────────────────────
    /**
     * Sample Request Body (JSON):
     * {
     *   "patientId": 1,
     *   "doctorId": 2,
     *   "appointmentDate": "2025-08-20",
     *   "slotTime": "10:30:00",
     *   "status": "SCHEDULED"
     * }
     *
     * The service will:
     *   1. Validate that patient and doctor exist.
     *   2. Auto-generate a queue tokenNumber for that doctor on that date.
     *   3. Save and return the appointment.
     */
    @PostMapping
    public ResponseEntity<String> addAppointment(@RequestBody AppointmentRequest request) {

        appointmentService.addAppointment(request);

        return ResponseEntity.ok("Appointment Booked Successfully");
    }    // ─────────────────────────────────────────────
    // GET /appointments
    // Retrieve all appointments
    // ─────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK); // 200 OK
    }

    // ─────────────────────────────────────────────
    // GET /appointments/{id}
    // Retrieve a specific appointment by ID
    // ─────────────────────────────────────────────
    /**
     * Example: GET /appointments/5
     * Returns appointment with id = 5, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // GET /appointments/patient/{patientId}
    // Retrieve all appointments for a specific patient
    // ─────────────────────────────────────────────
    /**
     * Bonus endpoint: useful for "My Appointments" patient dashboard.
     * Example: GET /appointments/patient/1
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(
            @PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // GET /appointments/doctor/{doctorId}
    // Retrieve all appointments for a specific doctor
    // ─────────────────────────────────────────────
    /**
     * Bonus endpoint: useful for doctor's daily schedule view.
     * Example: GET /appointments/doctor/2
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(
            @PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // PUT /appointments/{id}
    // Update an existing appointment (date, time, status, doctor)
    // ─────────────────────────────────────────────
    /**
     * Example: PUT /appointments/5
     * Common use case: cancel or reschedule.
     *
     * Sample body to cancel:
     * {
     *   "status": "CANCELLED"
     * }
     *
     * Sample body to reschedule:
     * {
     *   "appointmentDate": "2025-08-25",
     *   "slotTime": "11:00:00",
     *   "status": "SCHEDULED"
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequest request) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, request);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // DELETE /appointments/{id}
    // Delete (hard-delete) an appointment
    // ─────────────────────────────────────────────
    /**
     * Example: DELETE /appointments/5
     * Tip: In production systems, prefer soft-delete (set status = "CANCELLED")
     * instead of actually deleting records, to preserve audit history.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>("Appointment deleted successfully.", HttpStatus.OK);
    }
}
