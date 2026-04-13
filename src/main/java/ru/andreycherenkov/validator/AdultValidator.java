package ru.andreycherenkov.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) return false;
        return birthDate.isBefore(now().minusYears(18)); //todo добавить возможность указывать возраст совершеннолетия в аннотации
    }
}
