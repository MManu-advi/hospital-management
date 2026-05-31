package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.LoginRequest;
import com.hospital.hospital_management.dto.LoginResponse;
import com.hospital.hospital_management.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController — handles login form submissions and session management.
 *
 * Uses @Controller (not @RestController) because it returns Thymeleaf view names,
 * not JSON.  The browser navigates to these views after form submission.
 */
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // ─────────────────────────────────────────────
    // GET /login — show login page
    // ─────────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login"; // → templates/login.html
    }

    // ─────────────────────────────────────────────
    // POST /login — process form submission
    // ─────────────────────────────────────────────
    /**
     * Thymeleaf form sends:  email, password, role (ADMIN or PATIENT)
     * On success → redirect to correct dashboard.
     * On failure → back to login page with error message.
     */
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            HttpSession session,
            Model model) {

        try {
            LoginRequest request = new LoginRequest(email, password);
            LoginResponse response;

            // Route to correct login method based on role selected in the form
            if ("ADMIN".equals(role)) {
                response = authService.adminLogin(request);
            } else {
                response = authService.patientLogin(request);
            }

            // Store user info in HTTP session so dashboards can read it
            session.setAttribute("userId",   response.getUserId());
            session.setAttribute("userRole", response.getRole());
            session.setAttribute("message",  response.getMessage());

            // Redirect to the correct dashboard
            if ("ADMIN".equals(response.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/patient/dashboard";
            }

        } catch (Exception ex) {
            // Login failed — send error message back to login page
            model.addAttribute("error", ex.getMessage());
            return "login"; // stay on login page
        }
    }

    // ─────────────────────────────────────────────
    // GET /logout — clear session and go home
    // ─────────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear all session data
        return "redirect:/";  // → home page
    }
}