package com.hospital.hospital_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Hospital Management System.
 *
 * @SpringBootApplication combines:
 *   - @Configuration       : marks this as a config class
 *   - @EnableAutoConfiguration : auto-configures Spring Boot
 *   - @ComponentScan       : scans all sub-packages for beans
 */
@SpringBootApplication
public class HospitalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
        System.out.println("✅ Hospital Management System is running on http://localhost:8080");
    }
}
