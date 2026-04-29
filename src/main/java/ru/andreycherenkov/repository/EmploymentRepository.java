package ru.andreycherenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andreycherenkov.entity.Employment;

public interface EmploymentRepository extends JpaRepository<Employment, Integer> {
}
