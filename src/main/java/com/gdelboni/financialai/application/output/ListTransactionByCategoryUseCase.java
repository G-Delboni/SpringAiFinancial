package com.gdelboni.financialai.application.output;

import com.gdelboni.financialai.domain.Category;
import com.gdelboni.financialai.domain.Transaction;
import com.gdelboni.financialai.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

public class ListTransactionByCategoryUseCase {
    private final TransactionRepository transactionRepository;

    public  ListTransactionByCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @Tool(name="list-transaction-by-category",description = "lista transações por categoria.")
    public List<PersistTransactionOutput> execute(Category category) {
        return transactionRepository.findAllByCategory(category).stream().map(PersistTransactionOutput::from).toList();
    };
}
