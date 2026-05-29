package com.hospital.hospital_management.dto;

import lombok.*;

/**
 * DoctorDto - used to send/receive doctor data via REST APIs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDto {

    private Long id;
    private String name;
    private String specialization;
    private Integer experience;
    private String availableTime;
    private String phone;
    private String email;
}