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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "scoring_id")
    private UUID scoringId;

    @Column(name = "score", nullable = false)
    private Integer score;

    @CreationTimestamp
    @Column(name = "calculated_at", nullable = false)
    private Instant calculatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private LoanApplication application;

}
