package com.example.revpay_p2.service;

import java.math.BigDecimal;

public interface PaymentService {

    boolean requestMoney(Long requesterId,
                         String requestedFromIdentifier,
                         BigDecimal amount,
                         String note);

    void viewIncomingRequests(Long userId);

    boolean acceptRequest(Long userId, Long requestId);

    boolean declineRequest(Long userId, Long requestId);

    boolean cancelRequest(Long userId, Long requestId);
}
