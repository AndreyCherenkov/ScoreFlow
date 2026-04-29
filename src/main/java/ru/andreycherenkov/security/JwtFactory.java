package ru.andreycherenkov.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtFactory {

    @Value("app.security.jwt")
    private static String KEY;

    @Value("spring.application.name")
    private static String applicationName;

    //todo user id validate
    public String generateToken(String userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuer(applicationName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuer(applicationName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
                .signWith(getKey())
                .compact();
    }

    public Jws<Claims> extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }

}
