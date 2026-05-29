package com.hospital.hospital_management.service;

import com.hospital.hospital_management.entity.Admin;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AdminService - contains business logic for Admin operations.
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────
    public Admin addAdmin(Admin admin) {
        // In production, hash admin.getPassword() before saving
        return adminRepository.save(admin);
    }

    // ─────────────────────────────────────────────
    // READ ALL
    // ─────────────────────────────────────────────
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // ─────────────────────────────────────────────
    // READ BY ID
    // ─────────────────────────────────────────────
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
    }

    // ─────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────
    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        existing.setUsername(updatedAdmin.getUsername());
        existing.setEmail(updatedAdmin.getEmail());
        if (updatedAdmin.getPassword() != null && !updatedAdmin.getPassword().isEmpty()) {
            existing.setPassword(updatedAdmin.getPassword());
        }

        return adminRepository.save(existing);
    }

    // ─────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        adminRepository.delete(admin);
    }
}