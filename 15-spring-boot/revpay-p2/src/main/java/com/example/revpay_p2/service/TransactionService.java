package com.example.revpay_p2.service;

import java.math.BigDecimal;

public interface TransactionService {

    boolean sendMoney(Long senderId,
                      String receiverIdentifier,
                      BigDecimal amount,
                      String note);

    void printTransactionHistory(Long userId);
}
