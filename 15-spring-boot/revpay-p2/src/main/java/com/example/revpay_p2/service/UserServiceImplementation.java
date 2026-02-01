package com.example.revpay_p2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.exception.AuthenticationException;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.security.PasswordUtil;
import com.example.revpay_p2.service.UserService;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

	private static final int MAX_LOGIN_ATTEMPTS = 5;

	private final UserRepository userRepository;

	public UserServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// ================= REGISTER =================

	@Override
	public boolean registerUser(User user) {

		try {
			user.setPasswordHash(PasswordUtil.hash(user.getPasswordHash()));
			user.setPinHash(PasswordUtil.hash(user.getPinHash()));

			if (user.getUserType() == null) {
				user.setUserType("PERSONAL");
			}

			user.setLocked(false);
			user.setFailedAttempts(0);

			userRepository.save(user);

			logger.info("User registered successfully: {}", user.getEmail());

			return true;

		} catch (Exception e) {
			logger.error("User registration failed", e);
			return false;
		}
	}

	// ================= LOGIN =================

	@Override
	public User login(String emailOrPhone, String password) throws AuthenticationException {

		try {
			User user = userRepository.findByEmailOrPhone(emailOrPhone, emailOrPhone).orElse(null);

			if (user == null) {
				throw new AuthenticationException("User not found");
			}

			if (user.isLocked()) {
				throw new AuthenticationException("Account is locked");
			}

			if (!PasswordUtil.verify(password, user.getPasswordHash())) {

				handleFailedLogin(user);

				throw new AuthenticationException("Invalid password");
			}

			// success login
			user.setFailedAttempts(0);
			user.setLocked(false);
			userRepository.save(user);

			logger.info("User logged in successfully: {}", user.getEmail());

			return user;

		} catch (AuthenticationException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Login error", e);
			throw new AuthenticationException("Login failed");
		}
	}

	// ================= FAILED ATTEMPTS =================

	private void handleFailedLogin(User user) {

		int attempts = user.getFailedAttempts() + 1;
		user.setFailedAttempts(attempts);

		if (attempts >= MAX_LOGIN_ATTEMPTS) {
			user.setLocked(true);
			logger.warn("User locked due to failed attempts: {}", user.getId());
		}

		userRepository.save(user);
	}

	// ================= CHANGE PASSWORD =================

	@Override
	public boolean changePassword(Long userId, String currentPassword, String newPassword) {

		try {
			User user = userRepository.findById(userId).orElse(null);

			if (user == null) {
				logger.warn("User not found for password change");
				return false;
			}

			if (!PasswordUtil.verify(currentPassword, user.getPasswordHash())) {
				logger.warn("Incorrect current password");
				return false;
			}

			user.setPasswordHash(PasswordUtil.hash(newPassword));
			userRepository.save(user);

			logger.info("Password updated for user {}", userId);

			return true;

		} catch (Exception e) {
			logger.error("Password change failed", e);
			return false;
		}
	}

	// ================= RESET PASSWORD =================

	@Override
	public boolean resetPassword(String emailOrPhone, String securityAnswer, String newPassword) {

		// Optional future logic – keeping placeholder

		logger.warn("Reset password not implemented yet");

		return false;
	}
}
