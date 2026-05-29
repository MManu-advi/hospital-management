package com.hospital.hospital_management.repository;

import com.hospital.hospital_management.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * AdminRepository - handles all database operations for Admin.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find admin by username (useful for login)
    Optional<Admin> findByUsername(String username);

    // Find admin by email
    Optional<Admin> findByEmail(String email);
}