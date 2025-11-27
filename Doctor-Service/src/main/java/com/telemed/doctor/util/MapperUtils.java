package com.telemed.doctor.util;

import org.springframework.stereotype.Component;

@Component
public class MapperUtils {

    // Generic check for null values when mapping
    public static <T> T safe(T value) {
        return value != null ? value : null;
    }

    // Example method to trim strings safely
    public static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
