package com.example.revpay_p2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.example.revpay_p2.model.User;

class UserTest {

    @Test
    void testUserSettersAndGetters() {

        User user = new User();

        user.setFullName("Dileep");
        user.setEmail("dileep@gmail.com");
        user.setPhone("1234567890");
        user.setPasswordHash("pass");
        user.setPinHash("1111");
        user.setUserType("PERSONAL");
        user.setLocked(false);
        user.setFailedAttempts(0);

        assertEquals("Dileep", user.getFullName());
        assertEquals("dileep@gmail.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
        assertEquals("pass", user.getPasswordHash());
        assertEquals("1111", user.getPinHash());
        assertEquals("PERSONAL", user.getUserType());
        assertFalse(user.isLocked());
        assertEquals(0, user.getFailedAttempts());
    }
}
