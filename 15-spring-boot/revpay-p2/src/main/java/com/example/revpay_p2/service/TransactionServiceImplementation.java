package com.example.revpay_p2.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.Transaction;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.model.Wallet;
import com.example.revpay_p2.repository.TransactionRepository;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.repository.WalletRepository;
import com.example.revpay_p2.service.TransactionService;

@Service
@Transactional
public class TransactionServiceImplementation implements TransactionService {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionServiceImplementation.class);

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public TransactionServiceImplementation(TransactionRepository transactionRepository,
                                             WalletRepository walletRepository,
                                             UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    // ================= SEND MONEY =================

    @Override
    public boolean sendMoney(Long senderId,
                             String receiverIdentifier,
                             BigDecimal amount,
                             String note) {

        try {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                logger.warn("Amount must be positive");
                return false;
            }

            User sender = userRepository.findById(senderId)
                    .orElseThrow(() -> new RuntimeException("Sender not found"));

            User receiver = userRepository
                    .findByEmailOrPhone(receiverIdentifier, receiverIdentifier)
                    .orElse(null);

            if (receiver == null) {
                logger.warn("Receiver not found");
                return false;
            }

            if (sender.getId().equals(receiver.getId())) {
                logger.warn("Cannot send money to self");
                return false;
            }

            Wallet senderWallet = walletRepository.findByUser(sender);
            Wallet receiverWallet = walletRepository.findByUser(receiver);

            if (senderWallet.getBalance().compareTo(amount) < 0) {
                logger.warn("Insufficient funds");
                return false;
            }

            // update balances
            senderWallet.setBalance(
                    senderWallet.getBalance().subtract(amount));

            receiverWallet.setBalance(
                    receiverWallet.getBalance().add(amount));

            walletRepository.save(senderWallet);
            walletRepository.save(receiverWallet);

            // record transaction
            Transaction transaction = new Transaction();
            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            transaction.setAmount(amount);
            transaction.setType("SEND");
            transaction.setStatus("SUCCESS");
            transaction.setNote(note);

            transactionRepository.save(transaction);

            logger.info("Money sent from {} to {}", senderId, receiver.getId());

            return true;

        } catch (Exception e) {
            logger.error("Send money failed", e);
            return false;
        }
    }

    // ================= HISTORY =================

    @Override
    public void printTransactionHistory(Long userId) {

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Transaction> sent =
                    transactionRepository.findBySender(user);

            List<Transaction> received =
                    transactionRepository.findByReceiver(user);

            if (sent.isEmpty() && received.isEmpty()) {
                logger.info("No transactions found for user {}", userId);
                return;
            }

            logger.info("Transaction history for user {}:", userId);

            for (Transaction t : sent) {
                logger.info("{} | Sent to {} | Amount {} | Status {} | Note {}",
                        t.getCreatedAt(),
                        t.getReceiver().getId(),
                        t.getAmount(),
                        t.getStatus(),
                        t.getNote());
            }

            for (Transaction t : received) {
                logger.info("{} | Received from {} | Amount {} | Status {} | Note {}",
                        t.getCreatedAt(),
                        t.getSender().getId(),
                        t.getAmount(),
                        t.getStatus(),
                        t.getNote());
            }

        } catch (Exception e) {
            logger.error("Failed to fetch transaction history", e);
        }
    }
}
