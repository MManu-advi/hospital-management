package com.hospital.hospital_management.service;

import com.hospital.hospital_management.dto.DoctorDto;
import com.hospital.hospital_management.entity.Doctor;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DoctorService - contains all business logic for Doctor operations.
 */
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────
    public DoctorDto addDoctor(DoctorDto dto) {
        Doctor doctor = Doctor.builder()
                .name(dto.getName())
                .specialization(dto.getSpecialization())
                .experience(dto.getExperience())
                .availableTime(dto.getAvailableTime())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapToDto(savedDoctor);
    }

    // ─────────────────────────────────────────────
    // READ ALL
    // ─────────────────────────────────────────────
    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────
    // READ BY ID
    // ─────────────────────────────────────────────
    public DoctorDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return mapToDto(doctor);
    }

    // ─────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────
    public DoctorDto updateDoctor(Long id, DoctorDto dto) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        existingDoctor.setName(dto.getName());
        existingDoctor.setSpecialization(dto.getSpecialization());
        existingDoctor.setExperience(dto.getExperience());
        existingDoctor.setAvailableTime(dto.getAvailableTime());
        existingDoctor.setPhone(dto.getPhone());
        existingDoctor.setEmail(dto.getEmail());

        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        return mapToDto(updatedDoctor);
    }

    // ─────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        doctorRepository.delete(doctor);
    }

    // ─────────────────────────────────────────────
    // HELPER - Entity → DTO
    // ─────────────────────────────────────────────
    private DoctorDto mapToDto(Doctor doctor) {
        DoctorDto dto = new DoctorDto();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setExperience(doctor.getExperience());
        dto.setAvailableTime(doctor.getAvailableTime());
        dto.setPhone(doctor.getPhone());
        dto.setEmail(doctor.getEmail());
        return dto;
    }
}