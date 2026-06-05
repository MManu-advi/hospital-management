package com.hospital.hospital_management.dto;


	import lombok.*;

	import java.time.LocalDate;
	import java.time.LocalTime;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class AppointmentResponse {

	    private Long id;
	    private Long patientId;
	    private String patientName;

	    private Long doctorId;
	    private String doctorName;

	    private LocalDate appointmentDate;
	    private LocalTime slotTime;

	    private Integer tokenNumber;
	    private String status;
	}

