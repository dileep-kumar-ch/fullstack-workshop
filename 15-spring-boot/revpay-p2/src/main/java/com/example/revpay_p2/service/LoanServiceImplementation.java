package com.example.revpay_p2.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.Loan;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.repository.LoanRepository;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.service.LoanService;

@Service
@Transactional
public class LoanServiceImplementation implements LoanService {

    private static final Logger logger =
            LoggerFactory.getLogger(LoanServiceImplementation.class);

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanServiceImplementation(LoanRepository loanRepository,
                                     UserRepository userRepository) {

        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    // ================= APPLY LOAN =================

    @Override
    public boolean applyLoan(Long businessUserId,
                             BigDecimal amount,
                             String purpose,
                             BigDecimal repaymentAmount) {

        try {
            User user = userRepository.findById(businessUserId)
                    .orElseThrow(() -> new RuntimeException("Business user not found"));

            Loan loan = new Loan();
            loan.setUser(user);
            loan.setAmount(amount);
            loan.setPurpose(purpose);
            loan.setRepaymentAmount(repaymentAmount);
            loan.setStatus("APPLIED");
            loan.setAppliedAt(LocalDateTime.now());

            loanRepository.save(loan);

            logger.info("Loan applied by user {}", businessUserId);

            return true;

        } catch (Exception e) {
            logger.error("Loan application failed", e);
            return false;
        }
    }

    // ================= LIST LOANS =================

    @Override
    public List<Loan> listLoans(Long businessUserId) {

        try {
            User user = userRepository.findById(businessUserId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return loanRepository.findByUser(user);

        } catch (Exception e) {
            logger.error("Failed to list loans for user {}", businessUserId, e);
            return List.of();
        }
    }

    // ================= UPDATE STATUS =================

    @Override
    public boolean updateLoanStatus(Long loanId, String status) {

        try {
            Loan loan = loanRepository.findById(loanId)
                    .orElseThrow(() -> new RuntimeException("Loan not found"));

            loan.setStatus(status);
            loan.setUpdatedAt(LocalDateTime.now());

            loanRepository.save(loan);

            logger.info("Loan {} status updated to {}", loanId, status);

            return true;

        } catch (Exception e) {
            logger.error("Failed to update loan status {}", loanId, e);
            return false;
        }
    }
}

