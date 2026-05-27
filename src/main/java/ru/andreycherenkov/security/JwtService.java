package ru.andreycherenkov.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {

    @Value("${app.security.jwt}")
    private String jwtSecret;

    @Value("${spring.application.name}")
    private String applicationName;


    //todo phone validate
    public String generateToken(String phoneNumber, UUID userId) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .claim("phoneNumber", phoneNumber)
                .claim("userId", userId)
                .setIssuer(applicationName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(String phoneNumber, UUID userId) { //todo mclaims map
        return Jwts.builder()
                .setSubject(phoneNumber)
                .claim("phoneNumber", phoneNumber)
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

    public String extractPhoneNumber(String token) {
        return extractClaims(token).getBody().getSubject(); //todo  читать
    }

    public String extractUserId(String token) {
        return extractClaims(token).getBody().get("userId").toString();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        var phoneNumber = extractPhoneNumber(token);
        return phoneNumber.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public Date extractExpiry(String bearerToken) {
        return extractClaims(bearerToken).getBody().getExpiration();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private Boolean isTokenExpired(String bearerToken) {
        return extractExpiry(bearerToken).before(new Date());
    }

}
