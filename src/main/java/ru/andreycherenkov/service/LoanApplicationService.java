package ru.andreycherenkov.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreycherenkov.api.dto.ApplicationCreateRequest;
import ru.andreycherenkov.api.dto.ApplicationCreateResponse;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.api.dto.LoanApplicationResponse;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.mapper.LoanApplicationMapper;
import ru.andreycherenkov.repository.ApplicationRepository;
import ru.andreycherenkov.repository.CustomerRepository;
import ru.andreycherenkov.repository.specification.LoanApplicationSpecification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Service
public class LoanApplicationService {

    private final CustomerRepository customerRepository;
    private final ApplicationRepository applicationRepository;

    private final LoanApplicationMapper applicationMapper;

    public LoanApplicationResponse getApplication(UUID applicationId) {
        var application = applicationRepository
//                .findBy(applicationId, currentUserId) //todo получать id из контекста
                .findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        return applicationMapper.fromEntity(application);
    }

    @Transactional
    public ApplicationCreateResponse createLoanApplication(ApplicationCreateRequest request) {
        customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new IllegalArgumentException("Customer not found") //todo define exception type, maybe needs custom type?
        );

        var application = new LoanApplication(
                request.getAmount(),
                ApplicationStatus.NEW,
                request.getTermsMonths(),
                request.getPurpose()
        );

        var saved = applicationRepository.save(application);
        return new ApplicationCreateResponse(
                saved.getApplicationId(),
                saved.getApplicationStatus(),
                LocalDateTime.ofInstant(saved.getCreatedAt(), ZoneId.systemDefault()) //todo fix zones
        );
    }

    @Transactional(readOnly = true)
    public Collection<LoanApplicationResponse> getAllByParams(
            UUID userId,
            ApplicationStatus status
    ) {
        var specs = Specification.where(LoanApplicationSpecification.hasUserId(userId))
                .and(LoanApplicationSpecification.hasApplicationStatus(status));

        var applications = applicationRepository.findAll(specs);

        return applications.stream()
                .map(applicationMapper::fromEntity)
                .toList();
    }

    public void deleteLoanApplication(UUID applicationId) {
        applicationRepository.findById(applicationId).orElseThrow(
                () -> new IllegalArgumentException("Application not found")
        );
        applicationRepository.deleteById(applicationId);
    }
}
