package com.example.revpay_p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.revpay_p2.model.PaymentMethod;
import com.example.revpay_p2.model.User;

@Repository
public interface PaymentMethodRepository 
        extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod> findByUser(User user);

    PaymentMethod findByUserAndIsDefaultTrue(User user);
}
