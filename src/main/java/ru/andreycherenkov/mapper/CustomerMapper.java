package ru.andreycherenkov.mapper;

import org.springframework.stereotype.Component;
import ru.andreycherenkov.dto.CustomerProfileResponse;
import ru.andreycherenkov.entity.Customer;

@Component
public class CustomerMapper {

    public CustomerProfileResponse profileFromCustomer(Customer customer) {
        return CustomerProfileResponse.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .secondName(customer.getSecondName())
                .patronymic(customer.getPatronymic())
                .birthDate(customer.getBirthDate())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .passportSeries(customer.getPassportSeries())
                .passportNumber(customer.getPassportNumber())
                .income(customer.getIncome())
                .employmentName(customer.getEmployment().getName())
                .build();
    }
}
