package com.example.revpay_p2.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.User;
import com.example.revpay_p2.model.Wallet;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.repository.WalletRepository;

@Service
@Transactional
public class WalletServiceImplementation implements WalletService {

	private static final Logger logger = LoggerFactory.getLogger(WalletServiceImplementation.class);

	private final WalletRepository walletRepository;
	private final UserRepository userRepository;

	public WalletServiceImplementation(WalletRepository walletRepository, UserRepository userRepository) {

		this.walletRepository = walletRepository;
		this.userRepository = userRepository;
	}

	// ================= GET BALANCE =================

	@Override
	public BigDecimal getBalance(Long userId) {

		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

			Wallet wallet = walletRepository.findByUser(user);

			if (wallet == null) {
				logger.warn("Wallet not found for user {}", userId);
				return BigDecimal.ZERO;
			}

			return wallet.getBalance();

		} catch (Exception e) {
			logger.error("Error fetching balance for user {}", userId, e);
			return BigDecimal.ZERO;
		}
	}

	// ================= ADD MONEY =================

	@Override
	public boolean addMoney(Long userId, BigDecimal amount) {

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			logger.warn("Add money amount must be positive");
			return false;
		}

		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

			Wallet wallet = walletRepository.findByUser(user);

			wallet.setBalance(wallet.getBalance().add(amount));

			walletRepository.save(wallet);

			logger.info("Added {} to wallet of user {}", amount, userId);

			return true;

		} catch (Exception e) {
			logger.error("Failed to add money", e);
			return false;
		}
	}

	// ================= WITHDRAW MONEY =================

	@Override
	public boolean withdrawMoney(Long userId, BigDecimal amount) {

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			logger.warn("Withdraw amount must be positive");
			return false;
		}

		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

			Wallet wallet = walletRepository.findByUser(user);

			if (wallet.getBalance().compareTo(amount) < 0) {
				logger.warn("Insufficient funds for user {}", userId);
				return false;
			}

			wallet.setBalance(wallet.getBalance().subtract(amount));

			walletRepository.save(wallet);

			logger.info("Withdrawn {} from wallet of user {}", amount, userId);

			return true;

		} catch (Exception e) {
			logger.error("Failed to withdraw money", e);
			return false;
		}
	}
}
