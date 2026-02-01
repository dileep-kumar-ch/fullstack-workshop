package com.example.revpay_p2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.revpay_p2.model.User;
import com.example.revpay_p2.model.Wallet;

@Repository
public interface WalletRepository 
        extends JpaRepository<Wallet, Long> {

    Wallet findByUser(User user);
}
