package com.gdelboni.financialai.infraestructure.configuration;

import com.gdelboni.financialai.application.output.PersistTransactionUseCase;
import com.gdelboni.financialai.domain.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class TransactionConfig {

    @Bean
    public PersistTransactionUseCase persistTransactionUseCase(TransactionRepository transactionRepository) {
        return new PersistTransactionUseCase(transactionRepository);
    }
}
