package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.DoctorDto;
import com.hospital.hospital_management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DoctorController - exposes REST API endpoints for Doctor operations.
 *
 * Base URL: /doctors
 * All methods return JSON automatically via @RestController.
 */
@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // ─────────────────────────────────────────────
    // POST /doctors
    // Add a new doctor to the system
    // ─────────────────────────────────────────────
    /**
     * Sample Request Body (JSON):
     * {
     *   "name": "Dr. Priya Sharma",
     *   "specialization": "Cardiologist",
     *   "experience": 10,
     *   "availableTime": "9:00 AM - 1:00 PM",
     *   "phone": "9876543210",
     *   "email": "priya@hospital.com"
     * }
     */
    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody DoctorDto doctorDto) {
        DoctorDto savedDoctor = doctorService.addDoctor(doctorDto);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED); // 201 Created
    }

    // ─────────────────────────────────────────────
    // GET /doctors
    // Retrieve all doctors
    // ─────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK); // 200 OK
    }

    // ─────────────────────────────────────────────
    // GET /doctors/{id}
    // Retrieve a specific doctor by their ID
    // ─────────────────────────────────────────────
    /**
     * Example: GET /doctors/3
     * Returns doctor with id = 3, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        DoctorDto doctor = doctorService.getDoctorById(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // PUT /doctors/{id}
    // Update an existing doctor's information
    // ─────────────────────────────────────────────
    /**
     * Example: PUT /doctors/3
     * Send updated fields in the JSON body.
     * Returns the updated doctor object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorDto doctorDto) {
        DoctorDto updatedDoctor = doctorService.updateDoctor(id, doctorDto);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // DELETE /doctors/{id}
    // Remove a doctor from the system
    // ─────────────────────────────────────────────
    /**
     * Example: DELETE /doctors/3
     * Returns a confirmation message on success.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>("Doctor deleted successfully.", HttpStatus.OK);
    }
}
