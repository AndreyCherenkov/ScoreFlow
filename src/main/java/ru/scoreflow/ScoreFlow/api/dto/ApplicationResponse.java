package ru.scoreflow.ScoreFlow.api.dto;

import enums.ApplicationStatus;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class ApplicationResponse {

    private final UUID uuid;
    private final ApplicationStatus status;
    private final LocalDateTime createdAt;

}
