package com.gdelboni.financialai.application.output;

import com.gdelboni.financialai.domain.Category;
import com.gdelboni.financialai.domain.Transaction;
import com.gdelboni.financialai.domain.TransactionRepository;

import java.util.List;

public class ListTransactionByCategoryUseCase {
    private final TransactionRepository transactionRepository;

    public  ListTransactionByCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<PersistTransactionOutput> execute(Category category) {
        return transactionRepository.findAllByCategory(category).stream().map(PersistTransactionOutput::from).toList();
    };
}
