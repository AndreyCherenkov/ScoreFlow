package ru.andreycherenkov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.enums.LoanPurpose;

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
    private LoanPurpose purpose;
    private Instant createdAt;
}
