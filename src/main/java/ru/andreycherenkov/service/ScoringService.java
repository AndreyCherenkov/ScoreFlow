package ru.andreycherenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreycherenkov.dto.ScoringResponse;
import ru.andreycherenkov.entity.ScoringResult;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.repository.ApplicationRepository;
import ru.andreycherenkov.repository.ScoringResultRepository;
import ru.andreycherenkov.scoring.ScoringRule;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ScoringService {

    private final List<ScoringRule> scoringRules;
    private final ScoringResultRepository scoringResultRepository;
    private final ApplicationRepository applicationRepository;

    private final LoanApplicationService applicationService;

    private static final int PASSING_SCORE = 150;

    @Transactional
    public ScoringResponse computeScore(UUID applicationId) {
        var application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found: " + applicationId));
        applicationService.updateStatus(application, ApplicationStatus.IN_REVIEW);

        var totalScore = scoringRules.stream()
                .mapToInt(rule -> rule.evaluate(application))
                .sum();

        var scoringResult = scoringResultRepository.save(new ScoringResult(
                totalScore,
                application
        ));

        if (totalScore >= PASSING_SCORE) {
            applicationService.updateStatus(application, ApplicationStatus.APPROVED);
        } else {
            applicationService.updateStatus(application, ApplicationStatus.REJECTED);
        }

        applicationRepository.save(application);
        return new ScoringResponse(
                scoringResult.getScoringId(),
                totalScore,
                application.getApplicationStatus()
        );
    }
}
