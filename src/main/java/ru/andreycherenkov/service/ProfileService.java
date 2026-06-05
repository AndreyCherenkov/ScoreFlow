package ru.andreycherenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.dto.CustomerProfileResponse;
import ru.andreycherenkov.exception.UserNotFound;
import ru.andreycherenkov.mapper.CustomerMapper;
import ru.andreycherenkov.repository.CustomerRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerProfileResponse getCustomerProfile(UUID userId) {
        var customer = customerRepository.findWithEmploymentById(userId)
                .orElseThrow(() -> new UserNotFound("User not found"));
        return customerMapper.profileFromCustomer(customer);
    }
}
