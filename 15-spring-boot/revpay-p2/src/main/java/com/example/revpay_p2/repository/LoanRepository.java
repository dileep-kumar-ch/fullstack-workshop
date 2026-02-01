package com.example.revpay_p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.revpay_p2.model.Loan;
import com.example.revpay_p2.model.User;

@Repository
public interface LoanRepository 
        extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);
}
