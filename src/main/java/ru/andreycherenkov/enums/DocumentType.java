package ru.andreycherenkov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentType {
    APPLICATION_PDF("APPLICATION_PDF"),
    SCORING_REPORT("SCORING_REPORT"),
    CREDIT_REPORT("CREDIT_REPORT"),
    CONTRACT("CONTRACT"),
    OTHER("OTHER");

    private final String type;
}
