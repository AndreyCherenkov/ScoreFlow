package ru.andreycherenkov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoanPurpose {
    CAR("CAR"),
    ;

    private final String purpose;

}
