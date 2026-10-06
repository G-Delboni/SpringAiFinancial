package com.gdelboni.financialai.infraestructure.http.response;

import com.gdelboni.financialai.application.output.PersistTransactionOutput;
import com.gdelboni.financialai.domain.Category;

import java.math.BigDecimal;

public record TransactionResponse(String id, Category category, String description, double amount) {
    public static TransactionResponse from(PersistTransactionOutput output) {
        return new TransactionResponse(output.id(),output.category(),output.description(),output.amount());
    }
}
