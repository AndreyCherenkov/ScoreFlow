package ru.andreycherenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andreycherenkov.entity.DocumentContent;

import java.util.UUID;

@Repository
public interface DocumentContentRepository extends JpaRepository<DocumentContent, UUID> {
}
