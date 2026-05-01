package ru.andreycherenkov.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.api.dto.AuthRequest;
import ru.andreycherenkov.api.dto.AuthResponse;


@AllArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        throw new UnsupportedOperationException("not implemented");

    }

}
