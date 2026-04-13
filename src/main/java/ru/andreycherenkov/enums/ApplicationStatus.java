package ru.andreycherenkov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatus {
    NEW("NEW"),
    IN_REVIEW("IN_REVIEW"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String status;
}
