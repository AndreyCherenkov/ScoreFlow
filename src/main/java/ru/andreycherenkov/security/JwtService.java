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

@Component
public class JwtService {

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

    public String generateRefreshToken(String phoneNumber) { //todo mclaims map
        return Jwts.builder()
                .claim("phoneNumber", phoneNumber)
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
        var claims = extractClaims(token);
        return claims.getBody().get("phoneNumber", String.class);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        var phoneNumber = extractPhoneNumber(token);
        return phoneNumber.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String bearerToken) {
        return extractExpiry(bearerToken).before(new Date());
    }

    public Date extractExpiry(String bearerToken) {
        return extractClaims(bearerToken).getBody().getExpiration();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }

}
