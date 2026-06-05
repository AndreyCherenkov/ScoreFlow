package ru.andreycherenkov.exception;

public class EmploymentNotFound extends RuntimeException {
    public EmploymentNotFound(String message) {
        super(message);
    }
}
