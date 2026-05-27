package ru.andreycherenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andreycherenkov.entity.ScoringResult;

import java.util.UUID;

public interface ScoringResultRepository extends JpaRepository<ScoringResult, UUID> {
}
