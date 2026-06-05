package ru.andreycherenkov.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message
    ) {
    }

    @ExceptionHandler({
            UserNotFound.class,
            EmploymentNotFound.class,
            LoanApplicationNotFound.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            IllegalArgumentException ex) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    /**
     * Ошибки валидации @Valid
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                message
        );
    }

    /**
     * Ошибки БД (Hibernate/JPA)
     */
    @ExceptionHandler({
            DataAccessException.class,
            SQLException.class,
            JpaSystemException.class
    })
    public ResponseEntity<ErrorResponse> handleDatabase(Exception ex) {

        log.error("Database error", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Database operation failed"
        );
    }

    /**
     * Конфликт данных
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrity(
            DataIntegrityViolationException ex) {

        log.error("Data integrity violation", ex);

        return buildResponse(
                HttpStatus.CONFLICT,
                "Data integrity violation"
        );
    }

    /**
     * Любая непредвиденная ошибка
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception ex) {

        log.error("Unexpected error", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error"
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );

        return ResponseEntity.status(status).body(response);
    }
}
