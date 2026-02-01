package com.example.revpay_p2.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "money_requests")
public class MoneyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who requested
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    // From whom
    @ManyToOne
    @JoinColumn(name = "requested_from_id", nullable = false)
    private User requestedFrom;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    private String status; // PENDING, ACCEPTED, DECLINED, CANCELED

    private String note;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public MoneyRequest() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRequestedFrom() {
        return requestedFrom;
    }

    public void setRequestedFrom(User requestedFrom) {
        this.requestedFrom = requestedFrom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
