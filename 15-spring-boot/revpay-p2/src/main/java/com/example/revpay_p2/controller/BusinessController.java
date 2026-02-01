package com.example.revpay_p2.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.revpay_p2.model.BusinessAccount;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.security.PinUtil;
import com.example.revpay_p2.service.BusinessAccountService;
import com.example.revpay_p2.service.InvoiceService;
import com.example.revpay_p2.service.LoanService;
import com.example.revpay_p2.service.NotificationService;
import com.example.revpay_p2.service.PaymentService;
import com.example.revpay_p2.service.TransactionService;
import com.example.revpay_p2.service.WalletService;
@Component
public class BusinessController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);

	private final BusinessAccountService businessAccountService;
	private final WalletService walletService;
	private final LoanService loanService;
	private final InvoiceService invoiceService;
	private final NotificationService notificationService;
	private final TransactionService transactionService;
	private final PaymentService paymentService;
	
	

	public BusinessController(BusinessAccountService businessAccountService, WalletService walletService,
			LoanService loanService, InvoiceService invoiceService, NotificationService notificationService,
			TransactionService transactionService, PaymentService paymentService) {

		this.businessAccountService = businessAccountService;
		this.walletService = walletService;
		this.loanService = loanService;
		this.invoiceService = invoiceService;
		this.notificationService = notificationService;
		this.transactionService = transactionService;
		this.paymentService = paymentService;
	}

	/* ================= MAIN MENU ================= */

	public  void showBusinessMenu(User user, Scanner scanner) {

		while (true) {

			logger.info("""

					===== BUSINESS MENU =====
					1. Wallet
					2. Transactions
					3. Loans
					4. Invoices
					5. Business Details
					6. Notifications
					0. Logout
					""");

			String choice = scanner.nextLine();

			switch (choice) {
			case "1" -> walletMenu(user, scanner);
			case "2" -> transactionMenu(user, scanner);
			case "3" -> loanMenu(user, scanner);
			case "4" -> invoiceMenu(user, scanner);
			case "5" -> viewBusinessDetails(user);
			case "6" -> viewNotifications(user);
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
			case "0" -> {
				return;
			}
			default -> logger.warn("Invalid wallet option");
			}
		}
	}

	private void viewBalance(User user, Scanner scanner) {

		if (!verifyPin(user, scanner))
			return;

		BigDecimal balance = walletService.getBalance(user.getId());

		logger.info("Current Balance: {}", balance);
	}

	private void addMoney(User user, Scanner scanner) {

		if (!verifyPin(user, scanner))
			return;

		logger.info("Enter amount:");

		try {
			BigDecimal amount = new BigDecimal(scanner.nextLine());

			boolean success = walletService.addMoney(user.getId(), amount);

			logger.info(success ? "Money added successfully." : "Add money failed.");

		} catch (Exception e) {
			logger.error("Invalid amount entered");
		}
	}

	private void withdrawMoney(User user, Scanner scanner) {

		if (!verifyPin(user, scanner))
			return;

		logger.info("Enter amount:");

		try {
			BigDecimal amount = new BigDecimal(scanner.nextLine());

			boolean success = walletService.withdrawMoney(user.getId(), amount);

			logger.info(success ? "Withdrawal successful." : "Withdrawal failed.");

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
			case "0" -> {
				return;
			}
			default -> logger.warn("Invalid transaction option");
			}
		}
	}

	private void sendMoney(User user, Scanner scanner) {

		if (!verifyPin(user, scanner))
			return;

		logger.info("Recipient:");
		String receiver = scanner.nextLine();

		logger.info("Amount:");
		BigDecimal amount = new BigDecimal(scanner.nextLine());

		logger.info("Note:");
		String note = scanner.nextLine();

		boolean success = transactionService.sendMoney(user.getId(), receiver, amount, note);

		logger.info(success ? "Money sent." : "Failed to send money.");
	}

	private void requestMoney(User user, Scanner scanner) {

		if (!verifyPin(user, scanner))
			return;

		logger.info("Request from:");
		String from = scanner.nextLine();

		logger.info("Amount:");
		BigDecimal amount = new BigDecimal(scanner.nextLine());

		logger.info("Note:");
		String note = scanner.nextLine();

		boolean success = paymentService.requestMoney(user.getId(), from, amount, note);

		logger.info(success ? "Request sent." : "Request failed.");
	}

	private void acceptRequest(User user, Scanner scanner) {

		if (!verifyPin(user, scanner))
			return;

		logger.info("Request ID:");
		Long id = Long.parseLong(scanner.nextLine());

		boolean success = paymentService.acceptRequest(user.getId(), id);

		logger.info(success ? "Request accepted." : "Failed to accept.");
	}

	private void declineRequest(User user, Scanner scanner) {

		if (!verifyPin(user, scanner))
			return;

		logger.info("Request ID:");
		Long id = Long.parseLong(scanner.nextLine());

		boolean success = paymentService.declineRequest(user.getId(), id);

		logger.info(success ? "Request declined." : "Failed to decline.");
	}

	/* ================= LOANS ================= */

	private void loanMenu(User user, Scanner scanner) {

		while (true) {

			logger.info("""

					--- Loans ---
					1. Apply Loan
					2. List Loans
					0. Back
					""");

			String choice = scanner.nextLine();

			switch (choice) {
			case "1" -> applyLoan(user, scanner);
			case "2" -> loanService.listLoans(user.getId());
			case "0" -> {
				return;
			}
			default -> logger.warn("Invalid loan option");
			}
		}
	}

	private void applyLoan(User user, Scanner scanner) {

		logger.info("Loan amount:");
		BigDecimal amount = new BigDecimal(scanner.nextLine());

		logger.info("Purpose:");
		String purpose = scanner.nextLine();

		logger.info("Repayment amount:");
		BigDecimal repayment = new BigDecimal(scanner.nextLine());

		boolean success = loanService.applyLoan(user.getId(), amount, purpose, repayment);

		logger.info(success ? "Loan submitted." : "Loan failed.");
	}

	/* ================= INVOICES ================= */

	private void invoiceMenu(User user, Scanner scanner) {

		while (true) {

			logger.info("""

					--- Invoices ---
					1. Create Invoice
					2. List Invoices
					3. Mark Paid
					0. Back
					""");

			String choice = scanner.nextLine();

			switch (choice) {
			case "1" -> createInvoice(user, scanner);
			case "2" -> invoiceService.listInvoices(user.getId());
			case "3" -> markInvoicePaid(scanner);
			case "0" -> {
				return;
			}
			default -> logger.warn("Invalid invoice option");
			}
		}
	}

	private void createInvoice(User user, Scanner scanner) {

		logger.info("Customer Name:");
		String name = scanner.nextLine();

		logger.info("Customer Email:");
		String email = scanner.nextLine();

		logger.info("Customer Phone:");
		String phone = scanner.nextLine();

		logger.info("Amount:");
		BigDecimal amount = new BigDecimal(scanner.nextLine());

		logger.info("Details:");
		String details = scanner.nextLine();

		logger.info("Due Date (YYYY-MM-DDTHH:MM):");
		LocalDateTime dueDate = LocalDateTime.parse(scanner.nextLine());

		boolean success = invoiceService.createInvoice(user.getId(), name, email, phone, amount, details, dueDate);

		logger.info(success ? "Invoice created." : "Invoice creation failed.");
	}

	private void markInvoicePaid(Scanner scanner) {

		logger.info("Invoice ID:");
		Long id = Long.parseLong(scanner.nextLine());

		boolean success = invoiceService.markInvoicePaid(id);

		logger.info(success ? "Invoice marked paid." : "Failed to update invoice.");
	}

	/* ================= OTHER ================= */

	private void viewBusinessDetails(User user) {

		BusinessAccount acc = businessAccountService.getBusinessAccount(user.getId());

		if (acc == null) {
			logger.info("No business details found");
			return;
		}

		logger.info("""
				Business Name: {}
				Type: {}
				Tax ID: {}
				Address: {}
				Verified: {}
				""", acc.getBusinessName(), acc.getBusinessType(), acc.getTaxId(), acc.getAddress(), acc.isVerified());
	}

	private void viewNotifications(User user) {

		notificationService.showUnreadNotifications(user.getId());
	}
}
