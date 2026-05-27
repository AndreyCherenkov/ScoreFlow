package ru.andreycherenkov.dto;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String phone;
    private String password;
}
