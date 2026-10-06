package com.gdelboni.financialai.infraestructure.http.request;

import com.gdelboni.financialai.application.input.PersistTransactionInput;
import com.gdelboni.financialai.domain.Category;

public record TransactionRequest(String description, Category category, long amount) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description,amount, category);
    }
}
