package com.gdelboni.financialai.infraestructure.http;

import com.gdelboni.financialai.application.PersistTransactionUseCase;
import com.gdelboni.financialai.application.output.ListTransactionByCategoryUseCase;
import com.gdelboni.financialai.domain.Category;
import com.gdelboni.financialai.infraestructure.http.request.TransactionRequest;
import com.gdelboni.financialai.infraestructure.http.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionByCategoryUseCase listTransactionByCategoryUseCase;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase,  ListTransactionByCategoryUseCase listTransactionByCategoryUseCase) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByCategoryUseCase = listTransactionByCategoryUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        var transaction = persistTransactionUseCase.execute(request.toInput());
        return TransactionResponse.from(transaction);
    }

    @GetMapping("/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> readTransactions(@PathVariable Category category) {
        return listTransactionByCategoryUseCase.execute(category).stream().map(TransactionResponse::from).toList();
    }
}
