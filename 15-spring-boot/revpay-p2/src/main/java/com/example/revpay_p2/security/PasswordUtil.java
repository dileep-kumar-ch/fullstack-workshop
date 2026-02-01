package com.example.revpay_p2.security;

import java.security.MessageDigest;

public class PasswordUtil {

    private PasswordUtil() {}

    // ================= HASH =================

    public static String hash(String plain) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(plain.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    // ================= VERIFY =================

    public static boolean verify(String plain, String hashed) {

        return hash(plain).equals(hashed);
    }
}
