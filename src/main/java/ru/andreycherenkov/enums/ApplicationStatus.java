package ru.andreycherenkov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public enum ApplicationStatus {
    REGISTRATION(
            "REGISTRATION",
            () -> "\"На регистрации\""
    ),
    NEW(
            "NEW",
            () -> "\"Создана\""
    ),

    IN_REVIEW(
            "IN_REVIEW",
            () -> "\"На проверке\""
    ),

    APPROVED(
            "APPROVED",
            () -> "\"Одобрена\""
    ),

    REJECTED(
            "REJECTED",
            () -> "\"Отклонена\""
    );

    private final String name;
    private final Supplier<String> rusTranslator;
}
