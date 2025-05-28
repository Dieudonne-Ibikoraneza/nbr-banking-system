package com.bnr.banking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "banking_transactions")
public class Banking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @NotNull(message = "Account is required")
    @Column(nullable = false)
    private String account;

    @Positive(message = "Amount must be positive")
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(name = "banking_date_time", nullable = false)
    private LocalDateTime bankingDateTime;

    @Column(name = "recipient_account")
    private String recipientAccount; // For transfers

    @Column(name = "description")
    private String description;

    // Constructors
    public Banking() {
        this.bankingDateTime = LocalDateTime.now();
    }

    public Banking(Customer customer, String account, BigDecimal amount,
                   TransactionType type) {
        this.customer = customer;
        this.account = account;
        this.amount = amount;
        this.type = type;
        this.bankingDateTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public LocalDateTime getBankingDateTime() { return bankingDateTime; }
    public void setBankingDateTime(LocalDateTime bankingDateTime) {
        this.bankingDateTime = bankingDateTime;
    }

    public String getRecipientAccount() { return recipientAccount; }
    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}