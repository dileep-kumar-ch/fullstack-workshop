package com.example.revpay_p2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.revpay_p2.model.BusinessAccount;
import com.example.revpay_p2.model.User;

public interface BusinessAccountRepository extends JpaRepository<BusinessAccount, Long> {

	BusinessAccount findByUser(User user);

	BusinessAccount findByUserId(Long userId);

}
