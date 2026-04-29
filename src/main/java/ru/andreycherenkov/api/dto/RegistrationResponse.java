package ru.andreycherenkov.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationResponse {
    private final String userId;
}
