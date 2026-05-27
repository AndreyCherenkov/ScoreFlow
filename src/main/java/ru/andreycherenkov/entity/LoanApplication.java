package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.enums.LoanPurpose;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//todo builder?
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "loan_applications")
public class LoanApplication {

    public LoanApplication(
            BigDecimal amount,
            ApplicationStatus applicationStatus,
            Integer termMonth,
            LoanPurpose purpose,
            Customer customer
    ) {
        this.amount = amount;
        this.applicationStatus = applicationStatus;
        this.termMonth = termMonth;
        this.purpose = purpose;
        this.customer = customer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "application_id")
    private UUID applicationId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    @Column(name = "application_status", nullable = false)
    private ApplicationStatus applicationStatus;

    @Column(name = "term_month")
    private Integer termMonth;

    @Enumerated(EnumType.STRING) // Меняем String на Enum
    @Column(name = "purpose")
    private LoanPurpose purpose;

    @Column(name = "created_at", nullable = false)
    private final Instant createdAt = Instant.now(); //todo fix

    //todo связи с сущностями
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScoringResult> scoringResults = new ArrayList<>();

    public void addScoringResult(ScoringResult scoringResult) {
        scoringResults.add(scoringResult);
    }

}
