package ru.andreycherenkov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public final class AuthResponse {
    private final UUID userId;
    private final String accessToken;
    private final String refreshToken;
}
