package ru.andreycherenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andreycherenkov.entity.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
