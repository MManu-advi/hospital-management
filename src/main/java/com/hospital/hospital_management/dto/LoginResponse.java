

package com.hospital.hospital_management.dto;

import lombok.*;

/**
 * LoginResponse - DTO returned after a login attempt.
 *
 * Sent back to the client (browser/Postman) after POST /auth/login.
 *
 * Fields:
 *  - success  : true if credentials matched, false otherwise
 *  - message  : human-readable result ("Login successful" / "Invalid password")
 *  - userId   : the ID of the logged-in user (admin or patient)
 *  - role     : "ADMIN" or "PATIENT" — used to redirect to the right dashboard
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private boolean success;
    private String  message;
    private Long    userId;
    private String  role;   // "ADMIN" | "PATIENT"
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
