package com.hospital.hospital_management.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * AppointmentRequest - DTO used when a patient books an appointment.
 * The client (frontend/Postman) sends this JSON body in the POST /appointments request.
 *
 * Example JSON:
 * {
 *   "patientId": 1,
 *   "doctorId": 2,
 *   "appointmentDate": "2025-08-15",
 *   "slotTime": "10:30:00"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {

    private Long patientId;         // ID of the patient booking the appointment
    private Long doctorId;          // ID of the doctor being consulted
    private LocalDate appointmentDate;
    private LocalTime slotTime;
    private String status;          // Optional; defaults to "SCHEDULED" in service layer


}