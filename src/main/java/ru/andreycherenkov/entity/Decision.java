package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "decisions")
public class Decision {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "decision_id")
    private UUID decisionId;

    @Column(name = "decision_date", nullable = false)
    private LocalDate decisionDate;

    //todo связи с сущностями
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "decision_reason_map",
            joinColumns = @JoinColumn(name = "decision_id"),
            inverseJoinColumns = @JoinColumn(name = "reason_id")
    )
    private List<DecisionReason> decisionReasons;
}
