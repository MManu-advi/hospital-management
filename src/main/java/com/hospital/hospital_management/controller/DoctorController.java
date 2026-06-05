package com.hospital.hospital_management.controller;

	import com.hospital.hospital_management.dto.DoctorDto;
	import com.hospital.hospital_management.service.DoctorService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;

	@RestController
	@RequestMapping("/doctors")
	public class DoctorController {

	    @Autowired
	    private DoctorService doctorService;

	    @PostMapping
	    public ResponseEntity<DoctorDto> addDoctor(@RequestBody DoctorDto doctorDto) {
	        return ResponseEntity.ok(doctorService.addDoctor(doctorDto));
	    }

	    @GetMapping
	    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
	        return ResponseEntity.ok(doctorService.getAllDoctors());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
	        return ResponseEntity.ok(doctorService.getDoctorById(id));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id,
	                                                  @RequestBody DoctorDto doctorDto) {
	        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDto));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
	        doctorService.deleteDoctor(id);
	        return ResponseEntity.ok("Doctor deleted successfully");
	    }
	}

