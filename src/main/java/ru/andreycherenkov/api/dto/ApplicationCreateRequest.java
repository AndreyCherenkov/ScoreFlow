package ru.andreycherenkov.api.dto;

import lombok.Getter;
import ru.andreycherenkov.enums.LoanPurpose;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApplicationCreateRequest {

    private final UUID customerId;
    private final BigDecimal amount;
    private final int termsMonths;
    private final String purpose;
    private final BigDecimal monthlyIncome; //todo пока что предполагаются только рубли -> добавить другие валюты -> фукнционал по конвертации
    private final BigDecimal existingDebt;

}
