package ru.andreycherenkov.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.andreycherenkov.entity.Customer;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.enums.ApplicationStatus;

import java.util.UUID;

public class LoanApplicationSpecification {

    private LoanApplicationSpecification() {}

    public static Specification<LoanApplication> hasUserId(UUID userId) {
        return (root, query, criteriaBuilder) ->
                userId == null ? null : criteriaBuilder.equal(root.get(Customer.Fields.customerId), userId);
    }

    public static Specification<LoanApplication> hasApplicationStatus(ApplicationStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get(LoanApplication.Fields.applicationStatus), status.getStatus());
    }
}
