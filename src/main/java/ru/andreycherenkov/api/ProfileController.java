package ru.andreycherenkov.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.dto.CustomerProfileResponse;
import ru.andreycherenkov.service.ProfileService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerProfileResponse> getCustomerProfile(@PathVariable UUID userId) {
        return ResponseEntity.ok(profileService.getCustomerProfile(userId));
    }
}
