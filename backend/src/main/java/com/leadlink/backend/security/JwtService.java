package com.leadlink.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import java.util.Map;
import java.util.HashMap;


@Service
public class JwtService {

    // Použití bezpečného generování klíče
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Expirace tokenu (třeba 1 hodina)
    private final long JWT_EXPIRATION_MS = 3600000; // 1 hodina

    // Metoda pro generování JWT tokenu
    // Metoda pro generování JWT tokenu
    public String generateToken(UserPrincipal userPrincipal) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userPrincipal.getAuthorities().stream()
                .findFirst().get().getAuthority()); // např. "ROLE_USER"

        return Jwts.builder()
                .setClaims(claims)  // Přidej claims do tokenu
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    // Metoda pro extrakci uživatelského jména z tokenu
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Metoda pro kontrolu, zda je token platný
    public boolean isTokenValid(String token) {
        try {
            String username = extractUsername(token);
            return username != null && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Metoda pro kontrolu expirace tokenu
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Metoda pro extrakci data expirace z tokenu
    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }



}
