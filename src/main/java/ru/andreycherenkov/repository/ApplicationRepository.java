package ru.andreycherenkov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.andreycherenkov.entity.LoanApplication;

import java.util.UUID;

//todo вынести в mongodb?
@Repository
public interface ApplicationRepository extends CrudRepository<LoanApplication, UUID> {
}
