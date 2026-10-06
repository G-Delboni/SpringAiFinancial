package com.gdelboni.financialai.application.input;

import com.gdelboni.financialai.domain.Category;

public record PersistTransactionInput(String description, Long amount, Category category) {
}
