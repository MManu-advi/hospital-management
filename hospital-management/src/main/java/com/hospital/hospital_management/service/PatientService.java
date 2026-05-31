package com.hospital.hospital_management.service;

import com.hospital.hospital_management.dto.PatientDto;
import com.hospital.hospital_management.entity.Patient;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PatientService - contains all business logic for Patient operations.
 *
 * The service layer sits between the controller (API layer) and repository (DB layer).
 * Controllers call service methods; services call repository methods.
 *
 * @Service marks this class as a Spring service bean (auto-detected by component scan).
 */
@Service
public class PatientService {

    // @Autowired tells Spring to inject the PatientRepository bean automatically
    @Autowired
    private PatientRepository patientRepository;

    // ─────────────────────────────────────────────
    // CREATE - Add a new patient
    // ─────────────────────────────────────────────
    public PatientDto addPatient(PatientDto dto) {
        // Convert DTO to Entity (entity is what gets saved in DB)
        Patient patient = Patient.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .password(dto.getPassword()) // TODO: hash password before saving
                .build();

        // Save to DB and get back the saved entity (with generated id)
        Patient savedPatient = patientRepository.save(patient);

        // Convert saved entity back to DTO and return
        return mapToDto(savedPatient);
    }

    // ─────────────────────────────────────────────
    // READ - Get all patients
    // ─────────────────────────────────────────────
    public List<PatientDto> getAllPatients() {
        // Fetch all patients from DB, convert each to DTO using Java streams
        return patientRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────
    // READ - Get a single patient by ID
    // ─────────────────────────────────────────────
    public PatientDto getPatientById(Long id) {
        // findById returns Optional<Patient>; orElseThrow throws 404 if not found
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return mapToDto(patient);
    }

    // ─────────────────────────────────────────────
    // UPDATE - Update patient details
    // ─────────────────────────────────────────────
    public PatientDto updatePatient(Long id, PatientDto dto) {
        // First check if patient exists
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        // Update only the fields that were provided
        existingPatient.setName(dto.getName());
        existingPatient.setAge(dto.getAge());
        existingPatient.setGender(dto.getGender());
        existingPatient.setPhone(dto.getPhone());
        existingPatient.setEmail(dto.getEmail());

        // Only update password if a new one is provided
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existingPatient.setPassword(dto.getPassword());
        }

        // save() performs UPDATE since the entity already has an id
        Patient updatedPatient = patientRepository.save(existingPatient);
        return mapToDto(updatedPatient);
    }

    // ─────────────────────────────────────────────
    // DELETE - Remove a patient
    // ─────────────────────────────────────────────
    public void deletePatient(Long id) {
        // Check if patient exists before deleting
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }

    // ─────────────────────────────────────────────
    // HELPER - Convert Patient Entity → PatientDto
    // ─────────────────────────────────────────────
    private PatientDto mapToDto(Patient patient) {
        PatientDto dto = new PatientDto();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setPhone(patient.getPhone());
        dto.setEmail(patient.getEmail());
        // NOTE: We intentionally do NOT set password in response DTO for security
        return dto;
    }
}
