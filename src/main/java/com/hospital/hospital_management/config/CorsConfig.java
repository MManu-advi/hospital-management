package com.hospital.hospital_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * CorsConfig - configures Cross-Origin Resource Sharing (CORS).
 *
 * CORS allows or blocks requests from different domains/ports.
 * Example: A React frontend running on http://localhost:3000 needs
 * permission to call this backend on http://localhost:8080.
 *
 * @Configuration tells Spring this class contains bean definitions.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures which origins, methods, and headers are allowed.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            // Apply to ALL endpoints in the application
            .addMapping("/**")

            // Allow requests from these origins (change to your frontend URL in production)
            .allowedOrigins(
                "http://localhost:3000",   // React default
                "http://localhost:4200",   // Angular default
                "http://localhost:5173",   // Vite/Vue default
                "http://localhost:8080"    // Same-origin (Postman, etc.)
            )

            // Allow these HTTP methods
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

            // Allow these headers in requests
            .allowedHeaders("*")

            // Allow cookies/auth headers to be sent cross-origin
            .allowCredentials(true)

            // Cache preflight response for 1 hour (reduces OPTIONS requests)
            .maxAge(3600);
    }
}