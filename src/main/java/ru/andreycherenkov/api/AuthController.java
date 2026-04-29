package ru.andreycherenkov.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.api.dto.AuthRequest;
import ru.andreycherenkov.api.dto.AuthResponse;
import ru.andreycherenkov.api.dto.RegistrationRequest;
import ru.andreycherenkov.api.dto.RegistrationResponse;
import ru.andreycherenkov.service.RegistrationService;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class AuthController {

    private RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(registrationService.register(request));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        throw new UnsupportedOperationException("not implemented");

    }

}
