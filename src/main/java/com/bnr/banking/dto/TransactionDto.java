package com.bnr.banking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransactionDto {
    @NotNull(message = "Account is required")
    private String account;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String recipientAccount; // For transfers
    private String description;

    // Constructors
    public TransactionDto() {}

    public TransactionDto(String account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    // Getters and Setters
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getRecipientAccount() { return recipientAccount; }
    public void setRecipientAccount(String recipientAccount) { this.recipientAccount = recipientAccount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}