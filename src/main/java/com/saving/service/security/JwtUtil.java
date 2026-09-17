package com.saving.service.security;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(
                secret.getBytes()
        );
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(
                        new Date()
                )
                .expiration(
                        new Date(
                                System.currentTimeMillis() + expiration
                        )
                )
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(
            String token,
            String username
    ) {
        String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username);
    }
}
