package ru.scoreflow.ScoreFlow.api.dto;

import enums.LoanPurpose;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class ApplicationRequest {

    private final BigDecimal amount;
    private final int termsMonths;
    private final LoanPurpose purpose;
    private final BigDecimal monthlyIncome; //todo пока что предполагаются только рубли -> добавить другие валюты -> фукнционал по конвертации
    private final BigDecimal existingDebt;

}
