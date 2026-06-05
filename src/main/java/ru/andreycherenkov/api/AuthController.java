package ru.andreycherenkov.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.dto.AuthRequest;
import ru.andreycherenkov.dto.AuthResponse;
import ru.andreycherenkov.dto.RefreshRequest;
import ru.andreycherenkov.dto.RefreshResponse;
import ru.andreycherenkov.service.AuthService;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authUser(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
    }
}
