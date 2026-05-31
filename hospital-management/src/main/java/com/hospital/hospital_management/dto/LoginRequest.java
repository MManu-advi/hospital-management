package com.hospital.hospital_management.dto;

import lombok.*;

/**
 * LoginRequest - DTO for login requests (patients or admins).
 *
 * Example JSON:
 * {
 *   "email": "john@example.com",
 *   "password": "secret123"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    private String email;
    private String password;
}
