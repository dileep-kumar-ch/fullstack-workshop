package com.example.revpay_p2.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.revpay_p2.model.Loan;

public interface LoanService {

    boolean applyLoan(Long businessUserId,
                      BigDecimal amount,
                      String purpose,
                      BigDecimal repaymentAmount);

    List<Loan> listLoans(Long businessUserId);

    boolean updateLoanStatus(Long loanId, String status);
}
