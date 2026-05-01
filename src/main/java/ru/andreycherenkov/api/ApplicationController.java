package ru.andreycherenkov.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.api.dto.ApplicationCreateRequest;
import ru.andreycherenkov.api.dto.ApplicationCreateResponse;
import ru.andreycherenkov.api.dto.LoanApplicationResponse;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.service.LoanApplicationService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private LoanApplicationService applicationService;

    //todo define response dto
    @GetMapping("/{applicationId}")
    public ResponseEntity<LoanApplicationResponse> getApplication(@PathVariable UUID applicationId) {
        return ResponseEntity.ok(applicationService.getApplication(applicationId));
    }

    @GetMapping
    public ResponseEntity<Collection<LoanApplicationResponse>> getAllApplications(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) ApplicationStatus status
    ) {
        return ResponseEntity.ok(applicationService.getAllByParams(userId, status));
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
