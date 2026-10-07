package com.gdelboni.financialai.infraestructure.http;

import com.gdelboni.financialai.application.output.PersistTransactionUseCase;
import com.gdelboni.financialai.application.output.ListTransactionByCategoryUseCase;
import com.gdelboni.financialai.domain.Category;
import com.gdelboni.financialai.infraestructure.http.request.TransactionRequest;
import com.gdelboni.financialai.infraestructure.http.response.TransactionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionByCategoryUseCase listTransactionByCategoryUseCase;
    private final TranscriptionModel transcriptionModel;
    private final ChatClient chatClient;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase,
                                 ListTransactionByCategoryUseCase listTransactionByCategoryUseCase,
                                 TranscriptionModel transcriptionModel,
                                 @Value("classpath:/prompts/system-message.st")Resource systemPrompt,
                                 ChatClient.Builder chatClientBuilder) throws IOException {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByCategoryUseCase = listTransactionByCategoryUseCase;
        this.transcriptionModel = transcriptionModel;
        this.chatClient = chatClientBuilder
                .defaultSystem(systemPrompt.getContentAsString(Charset.defaultCharset()))
                .defaultTools(persistTransactionUseCase, listTransactionByCategoryUseCase)
                .build();
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

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String transcribeAudio(@RequestParam("file") MultipartFile file) {
        var resource = file.getResource();
        var userPrompt = transcriptionModel.transcribe(resource);

        return chatClient.prompt().user(userPrompt).call().content();
    }
}
