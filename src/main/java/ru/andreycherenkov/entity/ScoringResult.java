package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "scoring_results")
public class ScoringResult {

    public ScoringResult(Integer score, LoanApplication application) {
        this.score = score;
        this.application = application;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "scoring_id")
    private UUID scoringId;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "calculated_at", nullable = false)
    private final Instant calculatedAt = Instant.now(); //todo fix

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private LoanApplication application;

}
