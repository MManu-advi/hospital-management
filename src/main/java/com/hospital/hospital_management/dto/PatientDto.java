package com.hospital.hospital_management.dto;

import lombok.*;

/**
 * PatientDto (Data Transfer Object) - used to send/receive patient data via REST APIs.
 *
 * Why use DTOs instead of Entities directly?
 *   - Keeps API input/output separate from database model
 *   - Prevents exposing sensitive fields (like password in responses)
 *   - Gives flexibility to change API shape without affecting DB schema
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {

    private Long id;           // Included in responses; not required in create requests
    private String name;
    private Integer age;
    private String gender;
    private String phone;
    private String email;
    private String password;   // Only used during registration; never return in GET responses
}