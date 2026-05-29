package com.hospital.hospital_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResourceNotFoundException - thrown when a requested entity is not found in the DB.
 *
 * @ResponseStatus(HttpStatus.NOT_FOUND) automatically returns HTTP 404
 * when this exception is thrown (if not handled by GlobalExceptionHandler).
 *
 * Usage example:
 *   throw new ResourceNotFoundException("Patient not found with id: " + id);
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // Call super() to pass the message to RuntimeException
    public ResourceNotFoundException(String message) {
        super(message);
    }
}