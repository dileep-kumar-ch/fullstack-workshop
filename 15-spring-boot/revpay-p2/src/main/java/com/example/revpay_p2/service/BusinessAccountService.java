package com.example.revpay_p2.service;

import com.example.revpay_p2.model.BusinessAccount;
import com.example.revpay_p2.model.User;

public interface BusinessAccountService {
	boolean registerBusinessUser(User user, BusinessAccount businessAccount);

	BusinessAccount getBusinessAccount(Long userId);

	boolean verifyBusinessAccount(Long userId, boolean verified);

}
