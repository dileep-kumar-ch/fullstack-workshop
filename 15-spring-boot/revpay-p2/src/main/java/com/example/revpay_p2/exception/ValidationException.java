package com.example.revpay_p2.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String m) {
        super(m);
    }
}