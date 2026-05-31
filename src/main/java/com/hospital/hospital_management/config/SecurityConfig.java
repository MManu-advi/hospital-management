package com.hospital.hospital_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig — configures Spring Security for the application.
 *
 * Spring Boot 3.x uses the SecurityFilterChain bean approach
 * instead of the old WebSecurityConfigurerAdapter (which is removed in Boot 3).
 *
 * Current policy (beginner-friendly / development mode):
 *   - CSRF disabled  → makes REST API calls from Postman / JS easier
 *   - All URLs open  → authentication is handled manually in AuthService
 *   - BCrypt encoder → used to hash & verify passwords
 *
 * When you are ready for production, replace permitAll() with proper
 * role-based rules, e.g.:
 *   .requestMatchers("/admin/**").hasRole("ADMIN")
 *   .requestMatchers("/patient/**").hasRole("PATIENT")
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * SecurityFilterChain bean — defines which requests are allowed / blocked.
     *
     * @param http the HttpSecurity builder injected by Spring
     * @return configured SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF protection.
            // CSRF is important for browser-based form apps in production,
            // but disabling it here keeps things simple while learning.
            .csrf(csrf -> csrf.disable())

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow all endpoints without login.
                // Replace with fine-grained rules before going to production.
                .anyRequest().permitAll()
            )

            // Disable the default Spring Security login page
            // (we have our own Thymeleaf login page)
            .formLogin(form -> form.disable())

            // Disable HTTP Basic popup in browser
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    /**
     * PasswordEncoder bean — uses BCrypt hashing algorithm.
     *
     * BCrypt automatically:
     *   - Generates a random salt for each password
     *   - Hashes the password + salt together
     *   - Makes brute-force attacks very slow
     *
     * Usage:
     *   String hashed = passwordEncoder.encode("plainPassword");
     *   boolean matches = passwordEncoder.matches("plainPassword", hashed);
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}