package com.example.Notification_Service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendOtpRequest(
        @Email @NotBlank String to
) {}
