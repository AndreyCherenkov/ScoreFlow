package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "credit_reports")
public class CreditReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_report_id", nullable = false)
    private UUID creditReportId;

    @PositiveOrZero
    @Column(name = "delinquencies_number", nullable = false)
    private Integer delinquenciesNumber; //todo document that number in months!


    @Column(name = "bureau_score", nullable = false)
    private Integer bureauScore;

    //todo связи
}
