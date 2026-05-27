package ru.andreycherenkov.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.dto.ScoringResponse;
import ru.andreycherenkov.service.ScoringService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/score")
public class ScoringController {

    private final ScoringService scoringService;

    @PostMapping("/{applicationId}")
    public ResponseEntity<ScoringResponse> computeScore(@PathVariable UUID applicationId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((scoringService.computeScore(applicationId)));
    }
}
