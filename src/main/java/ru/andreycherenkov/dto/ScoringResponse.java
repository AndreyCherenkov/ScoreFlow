package ru.andreycherenkov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.andreycherenkov.enums.ApplicationStatus;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ScoringResponse {
    private final UUID scoringId;
    private final Integer score;
    private final ApplicationStatus applicationStatus;
}
