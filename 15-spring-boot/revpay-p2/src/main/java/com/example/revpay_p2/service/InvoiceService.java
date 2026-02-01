package com.example.revpay_p2.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.revpay_p2.model.Invoice;

public interface InvoiceService {

    boolean createInvoice(Long businessUserId,
                          String customerName,
                          String customerEmail,
                          String customerPhone,
                          BigDecimal amount,
                          String details,
                          LocalDateTime dueDate);

    List<Invoice> listInvoices(Long businessUserId);

    boolean markInvoicePaid(Long invoiceId);
}
