package ru.andreycherenkov.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.andreycherenkov.entity.LoanApplication;

import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<LoanApplication, UUID>,
        JpaSpecificationExecutor<LoanApplication> { }
