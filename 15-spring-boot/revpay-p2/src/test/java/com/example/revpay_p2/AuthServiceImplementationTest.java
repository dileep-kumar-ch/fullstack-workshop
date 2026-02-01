package com.example.revpay_p2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.User;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.repository.WalletRepository;
import com.example.revpay_p2.service.AuthService;

@SpringBootTest
@Transactional
class AuthServiceImplementationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    void cleanDb() {
        walletRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testUserRegistrationCreatesWallet() {

        User user = new User();
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setPhone("9999999999");
        user.setPasswordHash("password123");
        user.setPinHash("1234");
        user.setUserType("PERSONAL");

        authService.register(user);

        User savedUser =
                userRepository.findByEmail("test@example.com").orElse(null);

        assertNotNull(savedUser);

        var wallet =
                walletRepository.findByUser(savedUser);

        assertNotNull(wallet);
        assertEquals(BigDecimal.ZERO, wallet.getBalance());
    }

    @Test
    void testLoginSuccess() {

        User user = new User();
        user.setFullName("Login User");
        user.setEmail("login@test.com");
        user.setPhone("8888888888");
        user.setPasswordHash("mypassword");
        user.setPinHash("1111");
        user.setUserType("PERSONAL");

        authService.register(user);

        User loggedIn =
                authService.login("login@test.com", "mypassword");

        assertNotNull(loggedIn);
        assertEquals("Login User", loggedIn.getFullName());
    }
}
