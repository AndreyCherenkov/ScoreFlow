package ru.andreycherenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.andreycherenkov.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByPhone(String phone);

    @Query("""
        select c
        from customers c
        join fetch c.employment
        where c.customerId = :id
    """)
    Optional<Customer> findWithEmploymentById(UUID id);

}
