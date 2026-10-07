package com.gdelboni.financialai.domain;

import jakarta.transaction.InvalidTransactionException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {
    private TransactionID transactionId;
    private String description;
    private Long amount;
    private Category category;

    public Transaction(String description, Category category, Long amount) throws InvalidTransactionException {
        if (description == null || description.isBlank() || description.length() > 255)
            throw new InvalidTransactionException("description must have 1 to 255 characters");
        if (category == null) throw new InvalidTransactionException("category is required");
        if (amount == null || amount <= 0) throw new InvalidTransactionException("amount must be positive");
        this.transactionId = new TransactionID();
        this.description = description;
        this.category = category;
        this.amount = amount;
    }
}
