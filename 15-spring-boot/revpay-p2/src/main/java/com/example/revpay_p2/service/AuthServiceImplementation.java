package com.example.revpay_p2.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.Notification;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.model.Wallet;
import com.example.revpay_p2.repository.NotificationRepository;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.repository.WalletRepository;
import com.example.revpay_p2.security.PasswordUtil;
import com.example.revpay_p2.security.PinUtil;

@Service
@Transactional
public class AuthServiceImplementation implements AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImplementation.class);

	private static final int MAX_FAILED_ATTEMPTS = 3;

	private final UserRepository userRepository;
	private final NotificationRepository notificationRepository;
	private final WalletRepository walletRepository; // ✅ ADD THIS

	public AuthServiceImplementation(UserRepository userRepository, NotificationRepository notificationRepository,
			WalletRepository walletRepository) {

		this.userRepository = userRepository;
		this.notificationRepository = notificationRepository;
		this.walletRepository = walletRepository;
	}

	/* ================= REGISTER ================= */

	@Override
	public void register(User user) {

		// ✅ Hash password
		user.setPasswordHash(PasswordUtil.hash(user.getPasswordHash()));

		// ✅ Hash PIN
		user.setPinHash(PinUtil.hash(user.getPinHash()));

		user.setLocked(false);
		user.setFailedAttempts(0);

		// ✅ Save user first
		User savedUser = userRepository.save(user);

		// ✅ CREATE WALLET FOR USER
		Wallet wallet = new Wallet();
		wallet.setUser(savedUser);
		wallet.setBalance(BigDecimal.ZERO);

		walletRepository.save(wallet);

		logger.info("User registered + wallet created: {}", savedUser.getEmail());
	}

	/* ================= LOGIN ================= */

	@Override
	public User login(String identifier, String password) {

		Optional<User> userOpt = userRepository.findByEmailOrPhone(identifier, identifier);

		if (userOpt.isEmpty()) {
			logger.warn("User not found: {}", identifier);
			return null;
		}

		User user = userOpt.get();

		// Check locked
		if (user.isLocked()) {
			logger.warn("Account locked for user {}", user.getId());
			return null;
		}

		// Check password
		if (!PasswordUtil.verify(password, user.getPasswordHash())) {

			handleFailedAttempt(user);
			logger.warn("Invalid password for user {}", user.getId());

			return null;
		}

		// ✅ Successful login
		resetAttempts(user);
		createLoginNotification(user);

		logger.info("Login successful for {}", user.getFullName());

		return user;
	}

	/* ================= CHANGE PASSWORD ================= */

	@Override
	public boolean changePassword(Long userId, String currentPassword, String newPassword) {

		Optional<User> userOpt = userRepository.findById(userId);

		if (userOpt.isEmpty()) {
			logger.warn("User not found {}", userId);
			return false;
		}

		User user = userOpt.get();

		if (!PasswordUtil.verify(currentPassword, user.getPasswordHash())) {
			logger.warn("Incorrect current password for {}", userId);
			return false;
		}

		user.setPasswordHash(PasswordUtil.hash(newPassword));

		userRepository.save(user);

		logger.info("Password updated for user {}", userId);

		return true;
	}

	/* ================= HELPERS ================= */

	private void handleFailedAttempt(User user) {

		int attempts = user.getFailedAttempts() + 1;
		user.setFailedAttempts(attempts);

		if (attempts >= MAX_FAILED_ATTEMPTS) {
			user.setLocked(true);
			logger.warn("User {} locked due to failed attempts", user.getId());
		}

		userRepository.save(user);
	}

	private void resetAttempts(User user) {

		user.setFailedAttempts(0);
		user.setLocked(false);

		userRepository.save(user);
	}

	private void createLoginNotification(User user) {

		Notification notification = new Notification();
		notification.setUser(user);
		notification.setMessage("New login detected");
		notification.setType("LOGIN");

		notificationRepository.save(notification);
	}
}
