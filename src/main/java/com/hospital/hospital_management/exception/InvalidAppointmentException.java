package com.hospital.hospital_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InvalidAppointmentException - thrown when appointment data fails business validation.
 *
 * Examples where this is thrown:
 *   - Appointment date is in the past
 *   - Slot time falls outside doctor's available hours
 *   - Patient already has an appointment with the same doctor on the same day
 *
 * @ResponseStatus(HttpStatus.BAD_REQUEST) returns HTTP 400 automatically
 * when this exception bubbles up to the controller (if not caught by GlobalExceptionHandler).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAppointmentException extends RuntimeException {

    /**
     * @param message – descriptive reason for the failure.
     *                  Example: "Appointment date cannot be in the past."
     */
    public InvalidAppointmentException(String message) {
        super(message);
    }
}