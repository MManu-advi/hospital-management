package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.LoginRequest;
import com.hospital.hospital_management.entity.Admin;
import com.hospital.hospital_management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AdminController - exposes REST API endpoints for Admin operations.
 *
 * Base URL: /admins
 *
 * Provides:
 *   POST   /admins          → Register a new admin
 *   GET    /admins          → List all admins
 *   GET    /admins/{id}     → Get admin by ID
 *   PUT    /admins/{id}     → Update admin
 *   DELETE /admins/{id}     → Delete admin
 *   POST   /admins/login    → Admin login (basic credential check)
 */
@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ─────────────────────────────────────────────
    // POST /admins
    // Register a new admin
    // ─────────────────────────────────────────────
    /**
     * Sample Request Body (JSON):
     * {
     *   "username": "admin01",
     *   "email": "admin01@hospital.com",
     *   "password": "securepassword"
     * }
     */
    @PostMapping
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.addAdmin(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED); // 201 Created
    }

    // ─────────────────────────────────────────────
    // GET /admins
    // Retrieve all admins
    // ─────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK); // 200 OK
    }

    // ─────────────────────────────────────────────
    // GET /admins/{id}
    // Retrieve a specific admin by ID
    // ─────────────────────────────────────────────
    /**
     * Example: GET /admins/1
     * Returns admin with id = 1, or 404 Not Found if missing.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getAdminById(id);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // PUT /admins/{id}
    // Update an admin's details
    // ─────────────────────────────────────────────
    /**
     * Example: PUT /admins/1
     * Send the fields to update in the JSON body.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable Long id,
            @RequestBody Admin admin) {
        Admin updatedAdmin = adminService.updateAdmin(id, admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // DELETE /admins/{id}
    // Remove an admin from the system
    // ─────────────────────────────────────────────
    /**
     * Example: DELETE /admins/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return new ResponseEntity<>("Admin deleted successfully.", HttpStatus.OK);
    }

    // ─────────────────────────────────────────────
    // POST /admins/login
    // Basic login check (email + password)
    // ─────────────────────────────────────────────
    /**
     * Sample Request Body (JSON):
     * {
     *   "email": "admin01@hospital.com",
     *   "password": "securepassword"
     * }
     *
     * NOTE: This is a simple credential check for learning purposes.
     * In a production app, use Spring Security + JWT tokens for authentication.
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody LoginRequest loginRequest) {
        // Fetch admin by email; throw 404 if not found
        Admin admin = adminService.getAllAdmins()
                .stream()
                .filter(a -> a.getEmail().equals(loginRequest.getEmail()))
                .findFirst()
                .orElse(null);

        if (admin == null) {
            return new ResponseEntity<>("Admin not found with this email.", HttpStatus.NOT_FOUND);
        }

        // Plain-text password comparison (only for learning!)
        // In production: use BCryptPasswordEncoder.matches(rawPassword, hashedPassword)
        if (admin.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>("Login successful! Welcome, " + admin.getUsername(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid password.", HttpStatus.UNAUTHORIZED); // 401
        }
    }
}
