package ru.andreycherenkov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshRequest {
    private String refreshToken;
}
