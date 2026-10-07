package com.gdelboni.financialai.application.output;

import com.gdelboni.financialai.application.input.PersistTransactionInput;
import com.gdelboni.financialai.domain.Transaction;
import com.gdelboni.financialai.domain.TransactionRepository;
import jakarta.transaction.InvalidTransactionException;
import org.springframework.ai.tool.annotation.Tool;

public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @Tool(name="persist-transaction", description = "persiste uma nova transação.")
    public PersistTransactionOutput execute(PersistTransactionInput persistTransactionInput) throws InvalidTransactionException {
        var transaction = transactionRepository.save(new Transaction(persistTransactionInput.description(),
                persistTransactionInput.category(),persistTransactionInput.amount()));

        return PersistTransactionOutput.from(transaction);
    }
}
