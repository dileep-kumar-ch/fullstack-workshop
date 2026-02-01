package com.example.revpay_p2.service;

import java.math.BigDecimal;

public interface WalletService {
	BigDecimal getBalance(Long userId);

	boolean addMoney(Long userId, BigDecimal amount);

	boolean withdrawMoney(Long userId, BigDecimal amount);

}
