package com.gdelboni.financialai.application;

import com.gdelboni.financialai.application.input.PersistTransactionInput;
import com.gdelboni.financialai.application.output.PersistTransactionOutput;
import com.gdelboni.financialai.domain.Category;
import com.gdelboni.financialai.domain.Transaction;
import com.gdelboni.financialai.domain.TransactionRepository;

import java.math.BigDecimal;

public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public PersistTransactionOutput execute(PersistTransactionInput persistTransactionInput) {
        var transaction = transactionRepository.save(new Transaction(persistTransactionInput.description(),
                persistTransactionInput.category(),persistTransactionInput.amount()));

        return PersistTransactionOutput.from(transaction);
    }
}
