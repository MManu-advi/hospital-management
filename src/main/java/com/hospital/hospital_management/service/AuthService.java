package com.hospital.hospital_management.service;

import com.hospital.hospital_management.dto.LoginRequest;
import com.hospital.hospital_management.dto.LoginResponse;
import com.hospital.hospital_management.entity.Admin;
import com.hospital.hospital_management.entity.Patient;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.AdminRepository;
import com.hospital.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService — handles login logic for both Admins and Patients.
 *
 * Flow:
 *   1. Client sends LoginRequest { email, password, role }
 *   2. AuthService looks up the user by email in the correct table
 *   3. BCrypt compares the raw password against the stored hash
 *   4. Returns LoginResponse with success flag, message, userId, and role
 *
 * Why BCrypt?
 *   Never store plain-text passwords. BCrypt hashes are one-way —
 *   even if the database is leaked, attackers cannot reverse the hash.
 */
@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * PasswordEncoder is injected from the SecurityConfig bean.
     * Spring auto-wires it because we declared it as a @Bean there.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ─────────────────────────────────────────────
    // ADMIN LOGIN
     public LoginResponse adminLogin(LoginRequest request) {

        // 1. Find admin by email — throw 404 if not found
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No admin account found with email: " + request.getEmail()));

        // 2. Compare raw password with BCrypt hash stored in DB
        //    passwordEncoder.matches("rawPassword", "$2a$10$hashed...") → true / false
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(), admin.getPassword());

        if (!passwordMatches) {
            // Throw exception — caught by GlobalExceptionHandler → HTTP 400
            throw new IllegalArgumentException("Invalid password for admin: " + request.getEmail());
        }

        // 3. Build and return success response
        LoginResponse response = new LoginResponse();

        response.setSuccess(true);
        response.setMessage("Admin login successful! Welcome, " + admin.getUsername());
        response.setUserId(admin.getId());
        response.setRole("ADMIN");
     
        return response;
     }
     

    public LoginResponse patientLogin(LoginRequest request) {

       
        Patient patient = patientRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No patient account found with email: " + request.getEmail()));

        // 2. Verify password using BCrypt
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(), patient.getPassword());

        if (!passwordMatches) {
            throw new IllegalArgumentException("Invalid password for patient: " + request.getEmail());
        }

        // 3. Return success response
        LoginResponse response = new LoginResponse();

        response.setSuccess(true);
        response.setMessage("Patient login successful!");
        response.setUserId(patient.getId());
        response.setRole("PATIENT");

        return response;

    }
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}