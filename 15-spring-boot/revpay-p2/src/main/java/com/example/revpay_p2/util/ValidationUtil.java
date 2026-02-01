package com.example.revpay_p2.util;

public class ValidationUtil {
    public static void notNull(Object o, String msg) {
        if (o == null) throw new RuntimeException(msg);
    }
}