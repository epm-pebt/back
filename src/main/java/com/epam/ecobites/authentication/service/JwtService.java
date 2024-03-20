package com.epam.ecobites.authentication.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key = Jwts.SIG.HS512.key().build();
    private final long expirationTimeMillis = 60 * 60 * 1000;

    public String buildJwt(String subject) {
        return Jwts.builder()
                .subject(subject)
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(key)
                .compact();
    }

    public String extractSubject(String jwt) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    public Date extractExpiration(String jwt) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getExpiration();
    }

    public boolean isJwtExpired(String jwt) {
        return extractExpiration(jwt).before(new Date(System.currentTimeMillis()));
    }
}
