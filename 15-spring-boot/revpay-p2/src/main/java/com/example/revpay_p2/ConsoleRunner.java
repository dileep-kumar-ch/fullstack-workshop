package com.example.revpay_p2;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.revpay_p2.controller.AuthController;
import com.example.revpay_p2.controller.BusinessController;
import com.example.revpay_p2.controller.PersonalController;
import com.example.revpay_p2.model.User;


@Component
@Profile("!test")
public class ConsoleRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ConsoleRunner.class);

	private  AuthController authController;
	private  BusinessController businessController;
	private  PersonalController personalController;

	// ✅ Spring injects all controllers here
	public ConsoleRunner(AuthController authController, BusinessController businessController,
			PersonalController personalController) {

		this.authController = authController;
		this.businessController = businessController;
		this.personalController = personalController;
	}

	@Override
	public void run(String... args) {

		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.println("\n====== REV PAY SYSTEM ======");
			System.out.println("1. Register Personal User");
			System.out.println("2. Register Business User");
			System.out.println("3. Login");
			System.out.println("0. Exit");
			System.out.print("Choice: ");

			String choice = scanner.nextLine();

			switch (choice) {

			case "1" -> authController.registerPersonalUser(scanner);

			case "2" -> authController.registerBusinessUser(scanner);

			case "3" -> {
				User loggedInUser = authController.login(scanner);

				if (loggedInUser != null) {
					routeAfterLogin(loggedInUser, scanner);
				}
			}

			case "0" -> {
				System.out.println("Goodbye 👋");
				System.exit(0);
			}

			default -> System.out.println("Invalid choice.");
			}
		}
	}

	// ================= ROUTING =================

	private void routeAfterLogin(User user, Scanner scanner) {

		logger.info("Logged in as {} ({})", user.getFullName(), user.getUserType());

		if ("BUSINESS".equalsIgnoreCase(user.getUserType())) {

			businessController.showBusinessMenu(user, scanner);

		} else if ("PERSONAL".equalsIgnoreCase(user.getUserType())) {

			personalController.showPersonalMenu(user, scanner);

		} else {

			System.out.println("Unknown user type: " + user.getUserType());
		}
	}
}
