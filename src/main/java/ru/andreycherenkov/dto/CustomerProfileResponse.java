package ru.andreycherenkov.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class CustomerProfileResponse {

    private UUID customerId;

    private String firstName;
    private String secondName;
    private String patronymic;

    private LocalDate birthDate;

    private String email;
    private String phone;

    private String passportSeries;
    private String passportNumber;

    private BigDecimal income;

    private String employmentName;
}
