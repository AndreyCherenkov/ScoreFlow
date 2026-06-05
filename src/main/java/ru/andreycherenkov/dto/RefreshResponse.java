package ru.andreycherenkov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshResponse {
    private String accessToken;
    private String refreshToken;
}
