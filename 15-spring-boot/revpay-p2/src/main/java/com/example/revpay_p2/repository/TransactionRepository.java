package com.example.revpay_p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.revpay_p2.model.Transaction;
import com.example.revpay_p2.model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findBySender(User sender);

	List<Transaction> findByReceiver(User receiver);
}
