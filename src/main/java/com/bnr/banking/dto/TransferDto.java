package com.bnr.banking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferDto {
    @NotNull(message = "Source account is required")
    private String sourceAccount;

    @NotNull(message = "Recipient account is required")
    private String recipientAccount;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String description;

    // Constructors
    public TransferDto() {}

    // Getters and Setters
    public String getSourceAccount() { return sourceAccount; }
    public void setSourceAccount(String sourceAccount) { this.sourceAccount = sourceAccount; }

    public String getRecipientAccount() { return recipientAccount; }
    public void setRecipientAccount(String recipientAccount) { this.recipientAccount = recipientAccount; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
