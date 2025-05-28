package com.bnr.banking.exception;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
