package com.gdelboni.financialai.application.output;

import com.gdelboni.financialai.domain.Category;
import com.gdelboni.financialai.domain.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record PersistTransactionOutput(String id, String description, Double amount, Category category) {
    public static PersistTransactionOutput from(Transaction transaction) {
        return new PersistTransactionOutput(
                transaction.getTransactionId().uuid().toString(),
                transaction.getDescription(),
                BigDecimal.valueOf(transaction.getAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue(),
                transaction.getCategory());
    }
}