package com.leadlink.backend.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Používáme stejný tajný klíč jako v JwtService
    private final String SECRET_KEY = "tajny_klic";  // Tento klíč musí být stejný jako v JwtService

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hodina

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)  // Použití stejného klíče
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Použití stejného klíče pro ověřování
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)  // Použití stejného klíče pro ověřování
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
