package com.example.revpay_p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.revpay_p2.model.Invoice;
import com.example.revpay_p2.model.User;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	List<Invoice> findByUser(User user);
}
