package com.example.revpay_p2.security;

import java.security.MessageDigest;

public class PinUtil {

    private PinUtil() {}

    // ================= HASH =================

    public static String hash(String pin) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(pin.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("PIN hashing failed", e);
        }
    }

    // ================= VERIFY =================

    public static boolean verify(String plainPin, String hashedPin) {

        return hash(plainPin).equals(hashedPin);
    }
}
