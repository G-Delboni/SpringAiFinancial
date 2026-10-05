package com.gdelboni.financialai;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENROUTER_API_KEY", matches = ".+")
public class OpenRouterChatModelIT {

    @Autowired
    private ChatModel chatModel;

    @Test
    void shouldReceiveResponseWhenOpenRouterIsCalled() {
        String prompt = "Gere um registro de budgeting, com descrição de gasto, valor em reais e local";

        ChatResponse response = chatModel.call(new Prompt(prompt));

        assertThat(response).isNotNull();
        assertThat(response.getResult()).isNotNull();

        String outputText = response.getResult().getOutput().getText();
        assertThat(outputText).isNotNull().isNotEmpty();

        System.out.println("========== RETORNO DO OPENROUTER ==========");
        System.out.println(outputText);
        System.out.println("===========================================");
    }
}
