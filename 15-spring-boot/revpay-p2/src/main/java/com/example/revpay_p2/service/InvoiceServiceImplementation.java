package com.example.revpay_p2.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.Invoice;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.repository.InvoiceRepository;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.service.InvoiceService;

@Service
@Transactional
public class InvoiceServiceImplementation implements InvoiceService {

    private static final Logger logger =
            LoggerFactory.getLogger(InvoiceServiceImplementation.class);

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public InvoiceServiceImplementation(InvoiceRepository invoiceRepository,
                                        UserRepository userRepository) {

        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
    }

    // ================= CREATE INVOICE =================

    @Override
    public boolean createInvoice(Long businessUserId,
                                 String customerName,
                                 String customerEmail,
                                 String customerPhone,
                                 BigDecimal amount,
                                 String details,
                                 LocalDateTime dueDate) {

        try {
            User user = userRepository.findById(businessUserId)
                    .orElseThrow(() -> new RuntimeException("Business user not found"));

            Invoice invoice = new Invoice();
            invoice.setUser(user);
            invoice.setCustomerName(customerName);
            invoice.setCustomerEmail(customerEmail);
            invoice.setCustomerPhone(customerPhone);
            invoice.setAmount(amount);
            invoice.setDetails(details);
            invoice.setDueDate(dueDate);
            invoice.setStatus("UNPAID");
            invoice.setCreatedAt(LocalDateTime.now());

            invoiceRepository.save(invoice);

            logger.info("Invoice created for business user {}", businessUserId);

            return true;

        } catch (Exception e) {
            logger.error("Failed to create invoice", e);
            return false;
        }
    }

    // ================= LIST INVOICES =================

    @Override
    public List<Invoice> listInvoices(Long businessUserId) {

        try {
            User user = userRepository.findById(businessUserId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return invoiceRepository.findByUser(user);

        } catch (Exception e) {
            logger.error("Failed to list invoices for user {}", businessUserId, e);
            return List.of();
        }
    }

    // ================= MARK PAID =================

    @Override
    public boolean markInvoicePaid(Long invoiceId) {

        try {
            Invoice invoice = invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));

            invoice.setStatus("PAID");

            invoiceRepository.save(invoice);

            logger.info("Invoice {} marked as PAID", invoiceId);

            return true;

        } catch (Exception e) {
            logger.error("Failed to mark invoice paid {}", invoiceId, e);
            return false;
        }
    }
}
