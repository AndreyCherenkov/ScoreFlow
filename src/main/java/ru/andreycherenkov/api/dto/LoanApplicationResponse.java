package ru.andreycherenkov.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.andreycherenkov.enums.ApplicationStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class LoanApplicationResponse {

    private UUID applicationId;
    private BigDecimal amount;
    private ApplicationStatus applicationStatus;
    private Integer termMonth;
    private String purpose;
    private Instant createdAt;
}
