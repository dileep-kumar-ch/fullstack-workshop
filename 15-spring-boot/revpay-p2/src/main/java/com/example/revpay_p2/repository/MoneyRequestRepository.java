package com.example.revpay_p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.revpay_p2.model.MoneyRequest;
import com.example.revpay_p2.model.User;

public interface MoneyRequestRepository extends JpaRepository<MoneyRequest, Long> {
	List<MoneyRequest> findByRequester(User requester);

	List<MoneyRequest> findByRequestedFrom(User requestedFrom);

}
