package ru.andreycherenkov.api.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class AuthResponse {
    private final String token;
    private final String refreshToken;
}
