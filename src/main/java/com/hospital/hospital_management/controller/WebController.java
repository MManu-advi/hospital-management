package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.dto.AppointmentRequest;
import com.hospital.hospital_management.dto.DoctorDto;
import com.hospital.hospital_management.dto.PatientDto;
import com.hospital.hospital_management.entity.Appointment;
import com.hospital.hospital_management.service.AppointmentService;
import com.hospital.hospital_management.service.DoctorService;
import com.hospital.hospital_management.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WebController — the main controller for all Thymeleaf HTML pages.
 *
 * Difference from REST controllers:
 *   - Returns String (view name) instead of ResponseEntity<JSON>
 *   - Uses Model to pass data to Thymeleaf templates
 *   - Handles HTML form bindings with @ModelAttribute
 *
 * All routes that render full HTML pages live here.
 */
@Controller
public class WebController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    // ══════════════════════════════════════════════
    // HOME
    // ══════════════════════════════════════════════

    @GetMapping("/")
    public String home(Model model) {
        // Pass counts for home page stats section
        model.addAttribute("totalPatients",     patientService.getAllPatients().size());
        model.addAttribute("totalDoctors",      doctorService.getAllDoctors().size());
        model.addAttribute("totalAppointments", appointmentService.getAllAppointments().size());
        return "index"; // → templates/index.html
    }

    // ══════════════════════════════════════════════
    // ADMIN DASHBOARD
    // ══════════════════════════════════════════════

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        // Guard: redirect to login if not logged in as admin
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }

        // Pass stats to dashboard cards
        model.addAttribute("totalPatients",     patientService.getAllPatients().size());
        model.addAttribute("totalDoctors",      doctorService.getAllDoctors().size());
        model.addAttribute("totalAppointments", appointmentService.getAllAppointments().size());
        model.addAttribute("welcomeMessage",    session.getAttribute("message"));
        return "admin-dashboard"; // → templates/admin-dashboard.html
    }

    // ══════════════════════════════════════════════
    // PATIENT DASHBOARD
    // ══════════════════════════════════════════════

    @GetMapping("/patient/dashboard")
    public String patientDashboard(HttpSession session, Model model) {
        if (!"PATIENT".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        Long patientId = (Long) session.getAttribute("userId");
        List<Appointment> myAppointments = appointmentService.getAppointmentsByPatient(patientId);
        model.addAttribute("appointments",   myAppointments);
        model.addAttribute("welcomeMessage", session.getAttribute("message"));
        return "patient-dashboard";
    }

    // ══════════════════════════════════════════════
    // DOCTOR DASHBOARD (admin views doctor list)
    // ══════════════════════════════════════════════

    @GetMapping("/admin/doctor-dashboard")
    public String doctorDashboard(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctor-dashboard";
    }

    // ══════════════════════════════════════════════
    // PATIENT MANAGEMENT
    // ══════════════════════════════════════════════

    /** GET /admin/patients — list all patients */
    @GetMapping("/admin/patients")
    public String listPatients(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    /** GET /admin/patients/add — show blank add-patient form */
    @GetMapping("/admin/patients/add")
    public String showAddPatientForm(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("patientDto", new PatientDto());
        return "add-patient";
    }

    /** POST /admin/patients/add — save new patient */
    @PostMapping("/admin/patients/add")
    public String addPatient(@ModelAttribute PatientDto patientDto, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        patientService.addPatient(patientDto);
        return "redirect:/admin/patients";
    }

    /** GET /admin/patients/edit/{id} — show pre-filled edit form */
    @GetMapping("/admin/patients/edit/{id}")
    public String showEditPatientForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("patientDto", patientService.getPatientById(id));
        return "add-patient"; // reuse the same form (th:action handles both add & edit)
    }

    /** POST /admin/patients/edit/{id} — update patient */
    @PostMapping("/admin/patients/edit/{id}")
    public String updatePatient(@PathVariable Long id,
                                @ModelAttribute PatientDto patientDto,
                                HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        patientService.updatePatient(id, patientDto);
        return "redirect:/admin/patients";
    }

    /** GET /admin/patients/delete/{id} — delete patient and redirect */
    @GetMapping("/admin/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        patientService.deletePatient(id);
        return "redirect:/admin/patients";
    }

    // ══════════════════════════════════════════════
    // DOCTOR MANAGEMENT
    // ══════════════════════════════════════════════

    @GetMapping("/admin/doctors")
    public String listDoctors(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping("/admin/doctors/add")
    public String showAddDoctorForm(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("doctorDto", new DoctorDto());
        return "add-doctor";
    }

    @PostMapping("/admin/doctors/add")
    public String addDoctor(@ModelAttribute DoctorDto doctorDto, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        doctorService.addDoctor(doctorDto);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/admin/doctors/edit/{id}")
    public String showEditDoctorForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("doctorDto", doctorService.getDoctorById(id));
        return "add-doctor";
    }

    @PostMapping("/admin/doctors/edit/{id}")
    public String updateDoctor(@PathVariable Long id,
                               @ModelAttribute DoctorDto doctorDto,
                               HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        doctorService.updateDoctor(id, doctorDto);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/admin/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        doctorService.deleteDoctor(id);
        return "redirect:/admin/doctors";
    }

    // ══════════════════════════════════════════════
    // APPOINTMENT MANAGEMENT
    // ══════════════════════════════════════════════

    @GetMapping("/admin/appointments")
    public String listAppointments(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments";
    }

    @GetMapping("/admin/appointments/book")
    public String showBookAppointmentForm(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("appointmentRequest", new AppointmentRequest());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors",  doctorService.getAllDoctors());
        return "book-appointment";
    }

    @PostMapping("/admin/appointments/book")
    public String bookAppointment(@ModelAttribute AppointmentRequest request, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        appointmentService.addAppointment(request);
        return "redirect:/admin/appointments";
    }

    @GetMapping("/admin/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        appointmentService.deleteAppointment(id);
        return "redirect:/admin/appointments";
    }

    // ── Patient books own appointment ──────────────

    @GetMapping("/patient/appointments/book")
    public String showPatientBookForm(HttpSession session, Model model) {
        if (!"PATIENT".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("appointmentRequest", new AppointmentRequest());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors",  doctorService.getAllDoctors());
        return "book-appointment";
    }

    @PostMapping("/patient/appointments/book")
    public String patientBookAppointment(@ModelAttribute AppointmentRequest request,
                                         HttpSession session) {
        if (!"PATIENT".equals(session.getAttribute("userRole"))) return "redirect:/login";
        appointmentService.addAppointment(request);
        return "redirect:/patient/dashboard";
    }
}