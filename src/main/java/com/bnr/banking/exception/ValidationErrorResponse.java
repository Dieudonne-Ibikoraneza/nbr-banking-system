package com.bnr.banking.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {

    private String errorCode;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    // Constructor
    public ValidationErrorResponse(String errorCode, String message, Map<String, String> errors, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
