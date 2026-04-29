package ru.andreycherenkov.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.api.dto.ApplicationCreateRequest;
import ru.andreycherenkov.api.dto.ApplicationResponse;
import ru.andreycherenkov.service.LoanApplicationService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private LoanApplicationService service;

    @PostMapping
    public ApplicationResponse createApplication(@RequestBody ApplicationCreateRequest request) {
        return service.createLoanApplication(request);
    }



}
