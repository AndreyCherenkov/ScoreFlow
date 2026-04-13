package ru.andreycherenkov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.api.dto.ApplicationCreateRequest;
import ru.andreycherenkov.api.dto.ApplicationResponse;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.repository.ApplicationRepository;
import ru.andreycherenkov.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@Service
public class LoanApplicationService {

    private CustomerRepository customerRepository;
    private ApplicationRepository applicationRepository;

    public ApplicationResponse createLoanApplication(ApplicationCreateRequest request) {
        //todo проверка существования пользователя

        var application = new LoanApplication(
                request.getAmount(),
                ApplicationStatus.NEW,
                request.getTermsMonths(),
                request.getPurpose()
        );

        var saved = applicationRepository.save(application);
        return new ApplicationResponse(
                saved.getApplicationId(),
                saved.getApplicationStatus(),
                LocalDateTime.ofInstant(saved.getCreatedAt(), ZoneId.systemDefault()) //todo fix zones
        );
    }

}
