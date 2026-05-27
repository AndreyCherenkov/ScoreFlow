package ru.andreycherenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andreycherenkov.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
