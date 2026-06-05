package ru.andreycherenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.dto.RegistrationRequest;
import ru.andreycherenkov.dto.RegistrationResponse;
import ru.andreycherenkov.entity.Customer;
import ru.andreycherenkov.exception.EmploymentNotFound;
import ru.andreycherenkov.repository.CustomerRepository;
import ru.andreycherenkov.repository.EmploymentRepository;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmploymentRepository employmentRepository;

    private final PasswordEncoder passwordEncoder;

    public RegistrationResponse register(RegistrationRequest request) {

        var employment = employmentRepository.findById(request.getEmploymentId())
                .orElseThrow(() -> new EmploymentNotFound("Employment not found: " + request.getEmploymentId()));

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
                        .phone(normalizePhone(request.getPhone()))
                        .employment(employment)
                        .build()
        );

        return new RegistrationResponse(String.valueOf(customer.getCustomerId()));
    }

    private static String normalizePhone(String phone) {

        return phone
                .replaceAll("[^0-9]", "");
    }
}
