package com.example.revpay_p2.controller;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.revpay_p2.model.BusinessAccount;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.service.AuthService;
import com.example.revpay_p2.service.BusinessAccountService;
import com.example.revpay_p2.util.ConsolePasswordReader;

@Component   // ✅ Only this
public class AuthController {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final BusinessAccountService businessAccountService;

    // ✅ Constructor injection (no @Autowired needed)

    public AuthController(AuthService authService,
                          BusinessAccountService businessAccountService) {

        this.authService = authService;
        this.businessAccountService = businessAccountService;
    }

    /* ================= REGISTER PERSONAL ================= */

    public void registerPersonalUser(Scanner scanner) {

        try {
            logger.info("---- Personal User Registration ----");

            logger.info("Full Name:");
            String fullName = scanner.nextLine();

            logger.info("Email:");
            String email = scanner.nextLine();

            logger.info("Phone:");
            String phone = scanner.nextLine();

            logger.info("Password:");
            String password = ConsolePasswordReader.readPassword(scanner);

            logger.info("Transaction PIN:");
            String pin = ConsolePasswordReader.readPassword(scanner);

            // ✅ Create User object
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPasswordHash(password); // service will hash
            user.setPinHash(pin);
            user.setUserType("PERSONAL");

            // ✅ Call correct service method
            authService.register(user);

            logger.info("Personal account registered successfully.");

        } catch (Exception e) {
            logger.error("Personal registration failed", e);
        }
    }

    /* ================= REGISTER BUSINESS ================= */

    public void registerBusinessUser(Scanner scanner) {

        try {
            logger.info("---- Business User Registration ----");

            logger.info("Full Name:");
            String fullName = scanner.nextLine();

            logger.info("Email:");
            String email = scanner.nextLine();

            logger.info("Phone:");
            String phone = scanner.nextLine();

            logger.info("Password:");
            String password = ConsolePasswordReader.readPassword(scanner);

            logger.info("Transaction PIN:");
            String pin = ConsolePasswordReader.readPassword(scanner);

            // ✅ Create User
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPasswordHash(password);
            user.setPinHash(pin);
            user.setUserType("BUSINESS");

            // ✅ Create BusinessAccount
            BusinessAccount business = new BusinessAccount();

            logger.info("Business Name:");
            business.setBusinessName(scanner.nextLine());

            logger.info("Business Type:");
            business.setBusinessType(scanner.nextLine());

            logger.info("Tax ID:");
            business.setTaxId(scanner.nextLine());

            logger.info("Address:");
            business.setAddress(scanner.nextLine());

            logger.info("Document Path:");
            String documentPath = scanner.nextLine();

            // ✅ Pass objects (not primitives)
            businessAccountService.registerBusinessUser(
                    user,
                   business
            );

            logger.info("Business account registered successfully.");

        } catch (Exception e) {
            logger.error("Business registration failed", e);
        }
    }

    /* ================= LOGIN ================= */

    public User login(Scanner scanner) {

        try {
            logger.info("---- Login ----");

            logger.info("Email or Phone:");
            String identifier = scanner.nextLine();

            logger.info("Password:");
            String password = ConsolePasswordReader.readPassword(scanner);

            User user = authService.login(identifier, password);

            if (user != null) {
                logger.info("Welcome {}", user.getFullName());
            }

            return user;

        } catch (Exception e) {
            logger.error("Login failed", e);
            return null;
        }
    }

    /* ================= CHANGE PASSWORD ================= */

    public void changePassword(User user, Scanner scanner) {

        try {
            logger.info("Current Password:");
            String current = ConsolePasswordReader.readPassword(scanner);

            logger.info("New Password:");
            String newPwd = ConsolePasswordReader.readPassword(scanner);

            boolean success =
                    authService.changePassword(user.getId(), current, newPwd);

            logger.info(success ? "Password changed." : "Password change failed.");

        } catch (Exception e) {
            logger.error("Change password error", e);
        }
    }
}
