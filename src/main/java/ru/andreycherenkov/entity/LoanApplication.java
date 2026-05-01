package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import ru.andreycherenkov.enums.ApplicationStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

//todo builder?
@NoArgsConstructor
@Getter
@FieldNameConstants
@Entity(name = "loan_applications")
public class LoanApplication {

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

    @Column(name = "purpose")
    private String purpose;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    //todo связи с сущностями


    public LoanApplication(
            BigDecimal amount,
            ApplicationStatus applicationStatus,
            Integer termMonth,
            String purpose
    ) {
        this.amount = amount;
        this.applicationStatus = applicationStatus;
        this.termMonth = termMonth;
        this.purpose = purpose;
    }
}
