package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.PatientDto;
import com.hospital.hospital_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PatientController - exposes REST API endpoints for Patient operations.
 *
 * @RestController = @Controller + @ResponseBody
 *   → All methods return JSON automatically (no need for @ResponseBody on each)
 *
 * @RequestMapping("/patients") sets the base URL for all endpoints in this class.
 */
@RestController
@RequestMapping("/patients")
public class PatientController {

    // Spring injects PatientService here automatically
    @Autowired
    private PatientService patientService;

    /**
     * POST /patients
     * Creates a new patient.
     *
     * @RequestBody reads the JSON body from the HTTP request and maps it to PatientDto.
     * ResponseEntity lets us control the HTTP status code returned.
     */
    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@RequestBody PatientDto patientDto) {
        PatientDto savedPatient = patientService.addPatient(patientDto);
        // HTTP 201 Created is the correct status when a new resource is created
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    /**
     * GET /patients
     * Returns a list of all patients.
     */
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * GET /patients/{id}
     * Returns a single patient by their ID.
     *
     * @PathVariable extracts the {id} from the URL.
     * Example: GET /patients/3 → id = 3
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        PatientDto patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    /**
     * PUT /patients/{id}
     * Updates an existing patient's details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientDto patientDto) {
        PatientDto updatedPatient = patientService.updatePatient(id, patientDto);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    /**
     * DELETE /patients/{id}
     * Deletes a patient by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        // HTTP 200 OK with a success message
        return new ResponseEntity<>("Patient deleted successfully.", HttpStatus.OK);
    }
}
