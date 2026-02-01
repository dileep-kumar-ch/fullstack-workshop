package com.example.revpay_p2.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.MoneyRequest;
import com.example.revpay_p2.model.Transaction;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.model.Wallet;
import com.example.revpay_p2.repository.MoneyRequestRepository;
import com.example.revpay_p2.repository.TransactionRepository;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.repository.WalletRepository;
import com.example.revpay_p2.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImplementation implements PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImplementation.class);

	private final MoneyRequestRepository moneyRequestRepository;
	private final TransactionRepository transactionRepository;
	private final WalletRepository walletRepository;
	private final UserRepository userRepository;

	public PaymentServiceImplementation(MoneyRequestRepository moneyRequestRepository,
			TransactionRepository transactionRepository, WalletRepository walletRepository,
			UserRepository userRepository) {

		this.moneyRequestRepository = moneyRequestRepository;
		this.transactionRepository = transactionRepository;
		this.walletRepository = walletRepository;
		this.userRepository = userRepository;
	}

	// ================= REQUEST MONEY =================

	@Override
	public boolean requestMoney(Long requesterId, String requestedFromIdentifier, BigDecimal amount, String note) {

		try {
			if (amount.compareTo(BigDecimal.ZERO) <= 0) {
				logger.warn("Invalid request amount");
				return false;
			}

			User requester = userRepository.findById(requesterId)
					.orElseThrow(() -> new RuntimeException("Requester not found"));

			User requestedFrom = userRepository.findByEmailOrPhone(requestedFromIdentifier, requestedFromIdentifier)
					.orElse(null);

			if (requestedFrom == null) {
				logger.warn("Requested user not found");
				return false;
			}

			if (requester.getId().equals(requestedFrom.getId())) {
				logger.warn("Cannot request money from self");
				return false;
			}

			MoneyRequest request = new MoneyRequest();
			request.setRequester(requester);
			request.setRequestedFrom(requestedFrom);
			request.setAmount(amount);
			request.setNote(note);
			request.setStatus("PENDING");

			moneyRequestRepository.save(request);

			logger.info("Money request sent from {} to {}", requesterId, requestedFrom.getId());

			return true;

		} catch (Exception e) {
			logger.error("Failed to request money", e);
			return false;
		}
	}

	// ================= VIEW INCOMING =================

	@Override
	public void viewIncomingRequests(Long userId) {

		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

			List<MoneyRequest> requests = moneyRequestRepository.findByRequestedFrom(user);

			if (requests.isEmpty()) {
				logger.info("No pending money requests");
				return;
			}

			logger.info("Pending money requests:");

			for (MoneyRequest r : requests) {
				logger.info("Request ID: {} | From: {} | Amount: {} | Note: {} | Status: {}", r.getId(),
						r.getRequester().getId(), r.getAmount(), r.getNote(), r.getStatus());
			}

		} catch (Exception e) {
			logger.error("Failed to view incoming requests", e);
		}
	}

	// ================= ACCEPT REQUEST =================

	@Override
	public boolean acceptRequest(Long userId, Long requestId) {

		try {
			MoneyRequest request = moneyRequestRepository.findById(requestId)
					.orElseThrow(() -> new RuntimeException("Request not found"));

			if (!request.getRequestedFrom().getId().equals(userId)) {
				logger.warn("Unauthorized accept attempt");
				return false;
			}

			if (!"PENDING".equals(request.getStatus())) {
				logger.warn("Request already processed");
				return false;
			}

			Wallet payerWallet = walletRepository.findByUser(request.getRequestedFrom());

			Wallet receiverWallet = walletRepository.findByUser(request.getRequester());

			if (payerWallet.getBalance().compareTo(request.getAmount()) < 0) {

				logger.warn("Insufficient funds");
				return false;
			}

			// deduct & add
			payerWallet.setBalance(payerWallet.getBalance().subtract(request.getAmount()));

			receiverWallet.setBalance(receiverWallet.getBalance().add(request.getAmount()));

			walletRepository.save(payerWallet);
			walletRepository.save(receiverWallet);

			// update request
			request.setStatus("ACCEPTED");
			moneyRequestRepository.save(request);

			// record transaction
			Transaction transaction = new Transaction();
			transaction.setSender(request.getRequestedFrom());
			transaction.setReceiver(request.getRequester());
			transaction.setAmount(request.getAmount());
			transaction.setType("REQUEST");
			transaction.setStatus("SUCCESS");
			transaction.setNote("Accepted request " + requestId);

			transactionRepository.save(transaction);

			logger.info("Money request {} accepted", requestId);

			return true;

		} catch (Exception e) {
			logger.error("Failed to accept request", e);
			return false;
		}
	}

	// ================= DECLINE =================

	@Override
	public boolean declineRequest(Long userId, Long requestId) {

		try {
			MoneyRequest request = moneyRequestRepository.findById(requestId)
					.orElseThrow(() -> new RuntimeException("Request not found"));

			if (!request.getRequestedFrom().getId().equals(userId)) {
				logger.warn("Unauthorized decline");
				return false;
			}

			if (!"PENDING".equals(request.getStatus())) {
				logger.warn("Request already processed");
				return false;
			}

			request.setStatus("DECLINED");
			moneyRequestRepository.save(request);

			logger.info("Request {} declined", requestId);

			return true;

		} catch (Exception e) {
			logger.error("Failed to decline request", e);
			return false;
		}
	}

	// ================= CANCEL =================

	@Override
	public boolean cancelRequest(Long userId, Long requestId) {

		try {
			MoneyRequest request = moneyRequestRepository.findById(requestId)
					.orElseThrow(() -> new RuntimeException("Request not found"));

			if (!request.getRequester().getId().equals(userId)) {
				logger.warn("Unauthorized cancel");
				return false;
			}

			if (!"PENDING".equals(request.getStatus())) {
				logger.warn("Request already processed");
				return false;
			}

			request.setStatus("CANCELED");
			moneyRequestRepository.save(request);

			logger.info("Request {} canceled", requestId);

			return true;

		} catch (Exception e) {
			logger.error("Failed to cancel request", e);
			return false;
		}
	}
}
