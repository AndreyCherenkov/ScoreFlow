package ru.andreycherenkov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.api.dto.ApplicationCreateRequest;
import ru.andreycherenkov.api.dto.ApplicationCreateResponse;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.repository.ApplicationRepository;
import ru.andreycherenkov.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@AllArgsConstructor
@Service
public class LoanApplicationService {

    private CustomerRepository customerRepository;
    private ApplicationRepository applicationRepository;

    public ApplicationCreateResponse getApplication(UUID applicationId) {

        throw new UnsupportedOperationException();
    }

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

    public void deleteLoanApplication(UUID applicationId) {
        applicationRepository.findById(applicationId).orElseThrow(
                () -> new IllegalArgumentException("Application not found")
        );
        applicationRepository.deleteById(applicationId);
    }

}
