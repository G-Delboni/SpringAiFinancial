package com.gdelboni.financialai.domain;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    Transaction findById(UUID id);
    Transaction save(Transaction transaction);
    List<Transaction> findAllByCategory(Category category);
}