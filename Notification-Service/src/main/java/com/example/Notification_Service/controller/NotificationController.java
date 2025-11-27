package com.example.Notification_Service.controller;

import com.example.Notification_Service.dto.SendEmailRequest;
import com.example.Notification_Service.dto.SendOtpRequest;
import com.example.Notification_Service.dto.VerifyOtpRequest;
import com.example.Notification_Service.service.EmailService;
import com.example.Notification_Service.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;
    private final OtpService otpService;

    public NotificationController(EmailService emailService, OtpService otpService) {
        this.emailService = emailService;
        this.otpService = otpService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody SendOtpRequest req) {
        try {
            String otp = otpService.generateOtpFor(req.to());
            emailService.sendHtmlMessage(req.to(), "Your TeleMedicine OTP", "otp-email",
                    Map.of("otp", otp, "minutes", 5, "appName", "TeleMedicine"));
            return ResponseEntity.ok(Map.of("message", "OTP sent"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest req) {
        boolean ok = otpService.verifyOtp(req.to(), req.otp());
        return ResponseEntity.ok(Map.of("verified", ok));
    }

    @PostMapping("/send-onboarding")
    public ResponseEntity<?> sendOnboarding(@Valid @RequestBody SendEmailRequest req) {
        try {
            System.out.println("request"+req);
            emailService.sendHtmlMessage(req.to(), req.subject(), "onboarding-email",
                    Map.of("name", "New User", "appName", "TeleMedicine"));
            return ResponseEntity.ok(Map.of("message", "Onboarding email sent"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/send-custom")
    public ResponseEntity<?> sendCustom(@Valid @RequestBody SendEmailRequest req) {
        try {
            // For a quick custom HTML body we can send it via a simple template 'custom-email' that accepts bodyHtml
            emailService.sendHtmlMessage(req.to(), req.subject(), "custom-email",
                    Map.of("bodyHtml", req.bodyHtml(), "appName", "TeleMedicine"));
            return ResponseEntity.ok(Map.of("message", "Custom email sent"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/appointment-booked")
    public ResponseEntity<?> sendAppointmentEmails(@RequestBody Map<String, String> req) {
        try {


            String patientEmail = req.get("patientEmail");
            String doctorEmail = req.get("doctorEmail");
            String doctorName = req.get("doctorName");
            String patientName = req.get("patientName");
            String appointmentDate = req.get("appointmentDate");
            String appointmentTime = req.get("appointmentTime");

            emailService.sendHtmlMessage(
                    patientEmail,
                    "Your Appointment is Confirmed",
                    "appointment-patient",
                    Map.of(
                            "patientName", patientName,
                            "doctorName", doctorName,
                            "appointmentDate", appointmentDate,
                            "appointmentTime", appointmentTime,
                            "appName", "TeleMedicine"
                    )
            );

            emailService.sendHtmlMessage(
                    doctorEmail,
                    "New Appointment Booked",
                    "appointment-doctor",
                    Map.of(
                            "patientName", patientName,
                            "doctorName", doctorName,
                            "appointmentDate", appointmentDate,
                            "appointmentTime", appointmentTime,
                            "appName", "TeleMedicine"
                    )
            );

            return ResponseEntity.ok(Map.of("message", "Appointment emails sent"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

}
