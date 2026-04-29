package ru.andreycherenkov.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.api.dto.RegistrationRequest;
import ru.andreycherenkov.api.dto.RegistrationResponse;
import ru.andreycherenkov.entity.Customer;
import ru.andreycherenkov.repository.CustomerRepository;
import ru.andreycherenkov.repository.EmploymentRepository;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final CustomerRepository customerRepository;
    private final EmploymentRepository employmentRepository;

    private final PasswordEncoder passwordEncoder;

    public RegistrationResponse register(RegistrationRequest request) {

        var employment = employmentRepository.findById(request.getEmploymentId())
                .orElseThrow(() -> new RuntimeException("Employment not found: " + request.getEmploymentId()));  //todo кастомное исключение

        var customer = customerRepository.save(
                Customer.builder()
                        .firstName(request.getFirstName())
                        .secondName(request.getSecondName())
                        .patronymic(request.getPatronymic())
                        .birthDate(request.getBirthDate())
                        .passwordHash(passwordEncoder.encode(request.getPassword()))
                        .passportSeries(request.getPassportSeries())
                        .passportNumber(request.getPassportNumber())
                        .income(request.getIncome())
                        .email(request.getEmail())
                        .phone(request.getPhone())
                        .employment(employment)
                        .build()
        );

        return new RegistrationResponse(String.valueOf(customer.getCustomerId()));
    }
}
