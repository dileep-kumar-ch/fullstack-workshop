package com.example.revpay_p2.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String m) {
        super(m);
    }
}