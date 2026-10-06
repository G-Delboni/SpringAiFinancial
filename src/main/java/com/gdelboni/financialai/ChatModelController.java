package com.gdelboni.financialai;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ChatModelController {
    private final ChatModel chatModel;
    public ChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chatmodel")
    String chatModel(String prompt) {
        return this.chatModel.call(prompt);
    }

}
