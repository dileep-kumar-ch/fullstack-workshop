package com.example.revpay_p2.util;

import java.io.Console;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConsolePasswordReader {

	private static final Logger logger = LoggerFactory.getLogger(ConsolePasswordReader.class);

	private ConsolePasswordReader() {
	}

	/**
	 * Reads password securely (no echo if console available)
	 */
	public static String readPassword(Scanner scanner) {

		try {
			Console console = System.console();

			if (console != null) {
				char[] passwordChars = console.readPassword("Enter password: ");

				if (passwordChars == null) {
					return null;
				}

				return new String(passwordChars);
			}

			// Fallback (IDE or non-console mode)
			logger.warn("Console not available, using visible input");

			return scanner.nextLine();

		} catch (Exception e) {
			logger.error("Failed to read password", e);
			return null;
		}
	}
}
