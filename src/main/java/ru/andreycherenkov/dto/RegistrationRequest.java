package ru.andreycherenkov.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
public class RegistrationRequest {

    @NotBlank
    @Size(max = 25)
    private String firstName;

    @NotBlank
    @Size(max = 25)
    private String secondName;

    @Size(max = 25)
    private String patronymic;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotBlank
    @Size(min = 4, max = 4)
    private String passportSeries;

    @NotBlank
    @Size(min = 6, max = 6)
    private String passportNumber;

    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2) //todo document it, values depends on database constrains/settings
    private BigDecimal income;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Integer employmentId;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
