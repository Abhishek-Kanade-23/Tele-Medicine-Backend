package com.example.Notification_Service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final int OTP_EXPIRY_SECONDS = 5 * 60; // 5 minutes
    private final Map<String, OtpEntry> store = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public String generateOtpFor(String email) {
        String otp = String.format("%06d", random.nextInt(1_000_000));
        store.put(email.toLowerCase(), new OtpEntry(otp, Instant.now().plusSeconds(OTP_EXPIRY_SECONDS)));
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        var key = email.toLowerCase();
        OtpEntry entry = store.get(key);
        if (entry == null) return false;
        if (Instant.now().isAfter(entry.expiresAt)) {
            store.remove(key);
            return false;
        }
        boolean matches = entry.otp.equals(otp);
        if (matches) store.remove(key);
        return matches;
    }

    // Periodic cleanup: every 10 minutes remove expired entries
    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void cleanup() {
        var now = Instant.now();
        store.entrySet().removeIf(e -> now.isAfter(e.getValue().expiresAt));
    }

    private record OtpEntry(String otp, Instant expiresAt) {}
}
