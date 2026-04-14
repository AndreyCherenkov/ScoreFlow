package ru.andreycherenkov.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.api.dto.ApplicationCreateRequest;
import ru.andreycherenkov.api.dto.ApplicationCreateResponse;
import ru.andreycherenkov.service.LoanApplicationService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private LoanApplicationService applicationService;

    //todo define response dto
    @GetMapping("/{applicationId}")
    public ApplicationCreateResponse getApplication(@PathVariable UUID applicationId) {
        return applicationService.getApplication(applicationId);
    }

    @GetMapping
    public List<ApplicationCreateResponse> getAllApplications() { //todo выборка по параметрам запроса
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public ApplicationCreateResponse createApplication(@RequestBody ApplicationCreateRequest request) {
        return applicationService.createLoanApplication(request);
    }

    @PutMapping
    public ApplicationCreateResponse updateApplication(@RequestBody ApplicationCreateRequest request) { //todo define update dto
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{applicationId}")
    public void deleteApplication(@PathVariable UUID applicationId) {
        applicationService.deleteLoanApplication(applicationId);
    }
}
