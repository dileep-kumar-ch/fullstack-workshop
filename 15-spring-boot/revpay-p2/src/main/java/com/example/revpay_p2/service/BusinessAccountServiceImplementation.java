package com.example.revpay_p2.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.BusinessAccount;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.repository.BusinessAccountRepository;
import com.example.revpay_p2.repository.UserRepository;

@Service
@Transactional
public class BusinessAccountServiceImplementation implements BusinessAccountService {

	private static final Logger logger = LoggerFactory.getLogger(BusinessAccountServiceImplementation.class);

	private final BusinessAccountRepository businessAccountRepository;
	private final UserRepository userRepository;
	private final AuthService authService;

	public BusinessAccountServiceImplementation(BusinessAccountRepository businessAccountRepository,
			UserRepository userRepository, AuthService authService) {

		this.businessAccountRepository = businessAccountRepository;
		this.userRepository = userRepository;

		this.authService = authService;

	}

	// ================= REGISTER BUSINESS ACCOUNT =================

	@Override
	public boolean registerBusinessUser(User user, BusinessAccount businessAccount) {

		try {
			logger.info("Registering business user");

			user.setUserType("BUSINESS");

			// ✅ REGISTER USER PROPERLY (hash + wallet)
			authService.register(user); // <-- IMPORTANT

			// ✅ Fetch saved user (now has ID)
			User savedUser = userRepository.findByEmail(user.getEmail()).orElseThrow();

			// ✅ Link business account
			businessAccount.setUser(savedUser);
			businessAccount.setCreatedAt(LocalDateTime.now());
			businessAccount.setVerified(false);

			businessAccountRepository.save(businessAccount);

			logger.info("Business account created for user {}", savedUser.getId());

			return true;

		} catch (Exception e) {
			logger.error("Failed to register business user", e);
			return false;
		}
	}

	// ================= FETCH =================

	@Override
	public BusinessAccount getBusinessAccount(Long userId) {

		try {
			return businessAccountRepository.findByUserId(userId);

		} catch (Exception e) {
			logger.error("Failed to fetch business account", e);
			return null;
		}
	}

	// ================= VERIFY =================

	@Override
	public boolean verifyBusinessAccount(Long userId, boolean verified) {

		try {
			BusinessAccount account = businessAccountRepository.findByUserId(userId);

			if (account == null) {
				throw new RuntimeException("Business account not found");
			}

			account.setVerified(verified);
			businessAccountRepository.save(account);

			logger.info("Business verification updated");

			return true;

		} catch (Exception e) {
			logger.error("Verification failed", e);
			return false;
		}
	}
}
