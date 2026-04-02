package ru.scoreflow.ScoreFlow.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.scoreflow.ScoreFlow.api.dto.ApplicationRequest;
import ru.scoreflow.ScoreFlow.api.dto.ApplicationResponse;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    @GetMapping
    public ApplicationResponse getApplication(@RequestBody ApplicationRequest request) {
        throw new UnsupportedOperationException("Not implemented");
    }



}
