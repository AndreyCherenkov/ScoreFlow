package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.andreycherenkov.validator.Adult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldNameConstants
@Entity(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "patronymic")
    private String patronymic;

    @Past
    @Adult
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String passwordHash;

    @NotBlank
    @Size(min = 4, max = 4)
    @Column(name = "passport_series", nullable = false)
    private String passportSeries;

    //todo unique passportNumber + passportSeries
    @NotBlank
    @Size(min = 6, max = 6)
    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "income", nullable = false)
    private BigDecimal income;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    //todo @PhoneNumber
    @Column(name = "phone", unique = true, nullable = false)
    private String phone;


    //todo внешние ключи
    @ManyToOne(fetch = FetchType.LAZY) //todo matbe many-to-many due to one man can work on many jobs
    @JoinColumn(name = "employment_id", nullable = false)
    private Employment employment;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<RefreshToken> tokens = new ArrayList<>();
}

