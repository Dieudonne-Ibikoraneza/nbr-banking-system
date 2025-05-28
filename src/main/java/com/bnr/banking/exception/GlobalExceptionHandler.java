package com.bnr.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("CUSTOMER_NOT_FOUND", ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerAlreadyExists(CustomerAlreadyExistsException ex) {
        ErrorResponse err = new ErrorResponse("CUSTOMER_ALREADY_EXISTS", ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        ErrorResponse err = new ErrorResponse("INSUFFICIENT_FUNDS", ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                "VALIDATION_ERROR", "Validation failed", errors, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ErrorResponse error = new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    public static class ErrorResponse {
        private String code;
        private String message;
        private LocalDateTime timestamp;

        public ErrorResponse(String code, String message, LocalDateTime timestamp) {
            this.code = code;
            this.message = message;
            this.timestamp = timestamp;
        }

        // Getters
        public String getCode() { return code; }
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    public static class ValidationErrorResponse {
        private String code;
        private String message;
        private Map<String, String> errors;
        private LocalDateTime timestamp;

        public ValidationErrorResponse(String code, String message, Map<String, String> errors, LocalDateTime timestamp) {
            this.code = code;
            this.message = message;
            this.errors = errors;
            this.timestamp = timestamp;
        }

        // Getters
        public String getCode() { return code; }
        public String getMessage() { return message; }
        public Map<String, String> getErrors() { return errors; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}