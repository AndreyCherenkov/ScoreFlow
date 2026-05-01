package ru.andreycherenkov.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.api.dto.RegistrationRequest;
import ru.andreycherenkov.api.dto.RegistrationResponse;
import ru.andreycherenkov.service.CustomerService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(customerService.register(request));
    }

}
