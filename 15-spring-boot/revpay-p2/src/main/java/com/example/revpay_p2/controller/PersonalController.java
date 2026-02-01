package com.example.revpay_p2.controller;

import java.math.BigDecimal;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.revpay_p2.model.User;
import com.example.revpay_p2.security.PinUtil;
import com.example.revpay_p2.service.*;

@Component
public class PersonalController {

    private static final Logger logger =
            LoggerFactory.getLogger(PersonalController.class);

    private final WalletService walletService;
    private final TransactionService transactionService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;

    public PersonalController(
            WalletService walletService,
            TransactionService transactionService,
            PaymentService paymentService,
            NotificationService notificationService) {

        this.walletService = walletService;
        this.transactionService = transactionService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    /* ================= MAIN MENU ================= */

    public void showPersonalMenu(User user, Scanner scanner) {

        while (true) {

            logger.info("""
                    
                    ===== PERSONAL USER MENU =====
                    1. Wallet
                    2. Transactions
                    3. Notifications
                    0. Logout
                    """);

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> walletMenu(user, scanner);
                case "2" -> transactionMenu(user, scanner);
                case "3" -> viewNotifications(user, scanner);
                case "0" -> {
                    logger.info("Logged out.");
                    return;
                }
                default -> logger.warn("Invalid option selected");
            }
        }
    }

    /* ================= PIN ================= */

    private boolean verifyPin(User user, Scanner scanner) {

        logger.info("Enter PIN:");
        String enteredPin = scanner.nextLine();

        if (PinUtil.verify(enteredPin, user.getPinHash())) {
            return true;
        }

        logger.warn("Invalid PIN");
        return false;
    }

    /* ================= WALLET ================= */

    private void walletMenu(User user, Scanner scanner) {

        while (true) {

            logger.info("""
                    
                    --- Wallet ---
                    1. View Balance
                    2. Add Money
                    3. Withdraw Money
                    0. Back
                    """);

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewBalance(user, scanner);
                case "2" -> addMoney(user, scanner);
                case "3" -> withdrawMoney(user, scanner);
                case "0" -> { return; }
                default -> logger.warn("Invalid wallet option");
            }
        }
    }

    private void viewBalance(User user, Scanner scanner) {

        if (!verifyPin(user, scanner)) return;

        BigDecimal balance = walletService.getBalance(user.getId());

        logger.info("Wallet Balance: {}", balance);
    }

    private void addMoney(User user, Scanner scanner) {

        if (!verifyPin(user, scanner)) return;

        logger.info("Enter amount:");

        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            boolean success = walletService.addMoney(user.getId(), amount);

            logger.info(success ? "Amount added successfully." : "Failed to add amount.");

        } catch (Exception e) {
            logger.error("Invalid amount entered");
        }
    }

    private void withdrawMoney(User user, Scanner scanner) {

        if (!verifyPin(user, scanner)) return;

        logger.info("Enter amount:");

        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            boolean success = walletService.withdrawMoney(user.getId(), amount);

            logger.info(success ? "Withdrawal successful." : "Failed to withdraw.");

        } catch (Exception e) {
            logger.error("Invalid amount entered");
        }
    }

    /* ================= TRANSACTIONS ================= */

    private void transactionMenu(User user, Scanner scanner) {

        while (true) {

            logger.info("""
                    
                    --- Transactions ---
                    1. Send Money
                    2. Request Money
                    3. Incoming Requests
                    4. Accept Request
                    5. Decline Request
                    6. Transaction History
                    0. Back
                    """);

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> sendMoney(user, scanner);
                case "2" -> requestMoney(user, scanner);
                case "3" -> paymentService.viewIncomingRequests(user.getId());
                case "4" -> acceptRequest(user, scanner);
                case "5" -> declineRequest(user, scanner);
                case "6" -> transactionService.printTransactionHistory(user.getId());
                case "0" -> { return; }
                default -> logger.warn("Invalid transaction option");
            }
        }
    }

    private void sendMoney(User user, Scanner scanner) {

        if (!verifyPin(user, scanner)) return;

        logger.info("Recipient (email/phone):");
        String receiver = scanner.nextLine();

        logger.info("Amount:");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        logger.info("Note:");
        String note = scanner.nextLine();

        boolean success =
                transactionService.sendMoney(user.getId(), receiver, amount, note);

        logger.info(success ? "Money sent successfully." : "Failed to send money.");
    }

    private void requestMoney(User user, Scanner scanner) {

        if (!verifyPin(user, scanner)) return;

        logger.info("Request from:");
        String from = scanner.nextLine();

        logger.info("Amount:");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        logger.info("Note:");
        String note = scanner.nextLine();

        boolean success =
                paymentService.requestMoney(user.getId(), from, amount, note);

        logger.info(success ? "Money request sent." : "Request failed.");
    }

    private void acceptRequest(User user, Scanner scanner) {

        if (!verifyPin(user, scanner)) return;

        logger.info("Request ID:");
        Long requestId = Long.parseLong(scanner.nextLine());

        boolean success =
                paymentService.acceptRequest(user.getId(), requestId);

        logger.info(success ? "Request accepted." : "Failed to accept request.");
    }

    private void declineRequest(User user, Scanner scanner) {

        if (!verifyPin(user, scanner)) return;

        logger.info("Request ID:");
        Long requestId = Long.parseLong(scanner.nextLine());

        boolean success =
                paymentService.declineRequest(user.getId(), requestId);

        logger.info(success ? "Request declined." : "Failed to decline request.");
    }

    /* ================= NOTIFICATIONS ================= */

    private void viewNotifications(User user, Scanner scanner) {

        if (!verifyPin(user, scanner)) return;

        notificationService.showUnreadNotifications(user.getId());
    }
}
