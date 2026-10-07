package com.gdelboni.financialai.infraestructure.configuration;

import com.gdelboni.financialai.application.output.ListTransactionByCategoryUseCase;
import com.gdelboni.financialai.domain.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindByCategoryConfig {

    @Bean
    public ListTransactionByCategoryUseCase ListTransactionByCategoryUseCase(TransactionRepository transactionRepository) {
        return new ListTransactionByCategoryUseCase(transactionRepository);
    }
}
