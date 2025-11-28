package com.tele_medicine.file_upload.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    
    private final String secret=""; // same as auth-service

    public String extractUserId(String auth) {
        String token = auth.replace("Bearer ", "");
        // System.out.println("Extracting userId from token: " + token);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

                System.out.println("Claims extracted: " + claims    );
        // return claims.get("userId").toString();
        return claims.getSubject();  // this returns "sub" from the token

    }
}

