package com.authentication_service.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtility {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    private Key key;

    // Runs AFTER @Value injects properties
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // 🔹 Generate JWT
    public String generateToken(Long userId, List<String> roles, String emailId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("roles", roles)
                .claim("emailId", emailId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Validate JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT");
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT");
        } catch (SecurityException e) {
            System.out.println("Invalid signature");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    // 🔹 Extract UserId (Subject)
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 🔹 Extract Roles
    public String extractRoles(String token) {
        return extractAllClaims(token).get("roles", String.class);
    }

    // 🔹 Extract Email
    public String extractEmailId(String token) {
        return extractAllClaims(token).get("emailId", String.class);
    }

    // 🔹 Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
