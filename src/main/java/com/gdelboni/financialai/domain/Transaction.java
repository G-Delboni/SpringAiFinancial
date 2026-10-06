package com.gdelboni.financialai.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {
    private TransactionID transactionId;
    private String description;
    private Long amount;
    private Category category;

    public Transaction(String description, Category category, Long amount) {
        this.transactionId = new TransactionID();
        this.description = description;
        this.category = category;
        this.amount = amount;
    }
}
