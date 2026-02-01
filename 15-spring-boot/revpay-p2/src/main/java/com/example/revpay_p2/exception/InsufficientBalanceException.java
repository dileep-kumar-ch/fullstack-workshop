package com.example.revpay_p2.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String m) {
        super(m);
    }
}