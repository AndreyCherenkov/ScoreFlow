package ru.andreycherenkov.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.dto.AuthRequest;
import ru.andreycherenkov.dto.AuthResponse;
import ru.andreycherenkov.dto.RefreshResponse;
import ru.andreycherenkov.entity.RefreshToken;
import ru.andreycherenkov.repository.CustomerRepository;
import ru.andreycherenkov.repository.RefreshTokenRepository;
import ru.andreycherenkov.security.JwtService;

import java.time.LocalDateTime;

//TODO REFRESH TOKEN FIX не все токены отзываются, есть дубликаты токенов
@RequiredArgsConstructor
@Service
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerRepository customerRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse authUser(AuthRequest request) {
        var phone = normalizePhone(request.getPhone());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(phone, request.getPassword())
        );

        var customer = customerRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        var accessToken = jwtService.generateToken(phone, customer.getCustomerId());
        var refreshToken = jwtService.generateRefreshToken(phone, customer.getCustomerId());


        var creationDate = LocalDateTime.now();
        var token = new RefreshToken(
                refreshToken,
                creationDate,
                creationDate.plusHours(24),
                false,
                customer
        );

        customer.addRefreshToken(token);

        return new AuthResponse(customer.getCustomerId(), accessToken, refreshToken);
    }

    @Transactional
    public RefreshResponse refresh(String refreshToken) {

        var storedToken = refreshTokenRepository.findByTokenHash(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found")); //todo custom exception

        if (storedToken.isRevoked()) {
            throw new RuntimeException("Token revoked");
        }

        if (storedToken.getExpirationDate().isBefore(LocalDateTime.now())) { //todo test
            throw new RuntimeException("Token expired");
        }

        var customer = storedToken.getCustomer();

        var newAccessToken = jwtService.generateToken(customer.getPhone(), customer.getCustomerId());
        var newRefreshToken = jwtService.generateRefreshToken(customer.getPhone(), customer.getCustomerId());

        var token = refreshTokenRepository.save(
                new RefreshToken(
                        newRefreshToken,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(24),
                        false,
                        customer
                )
        );
        customer.addRefreshToken(token);
        storedToken.setRevoked(true); //todo SET ONLY IF REFRESH TOKEN IS EXPIRED

        return new RefreshResponse(
                newAccessToken,
                newRefreshToken
        );
    }

    private static String normalizePhone(String phone) {

        return phone
                .replaceAll("[^0-9]", "");
    }
}
