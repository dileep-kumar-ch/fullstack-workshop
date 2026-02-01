package com.example.revpay_p2;

import com.example.revpay_p2.model.User;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceImplementationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testRegisterUserSuccess() {

        User user = new User();
        user.setFullName("Test User");
        user.setEmail("test@test.com");
        user.setPhone("9999999999");
        user.setPasswordHash("password123");
        user.setPinHash("1234");

        boolean result = userService.registerUser(user);

        assertTrue(result);

        User saved = userRepository.findByEmail("test@test.com").orElse(null);

        assertNotNull(saved);
    }

    @Test
    void testLoginSuccess() {

        User user = new User();
        user.setFullName("Login User");
        user.setEmail("login@test.com");
        user.setPhone("8888888888");
        user.setPasswordHash("mypassword");
        user.setPinHash("1111");

        userService.registerUser(user);

        User loggedIn = userService.login("login@test.com", "mypassword");

        assertNotNull(loggedIn);
    }

    @Test
    void testChangePassword() {

        User user = new User();
        user.setFullName("Change User");
        user.setEmail("change@test.com");
        user.setPhone("7777777777");
        user.setPasswordHash("oldpass");
        user.setPinHash("2222");

        userService.registerUser(user);

        User saved = userRepository.findByEmail("change@test.com").orElse(null);

        boolean changed = userService.changePassword(
                saved.getId(),
                "oldpass",
                "newpass"
        );

        assertTrue(changed);
    }
}
