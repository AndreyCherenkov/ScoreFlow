package ru.andreycherenkov.api.dto;

import lombok.Getter;
import ru.andreycherenkov.enums.ApplicationStatus;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApplicationCreateResponse {

    private final UUID uuid;
    private final ApplicationStatus status;
    private final LocalDateTime createdAt;

}
