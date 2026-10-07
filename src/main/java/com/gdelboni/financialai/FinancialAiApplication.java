package com.gdelboni.financialai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;

@SpringBootApplication
public class FinancialAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialAiApplication.class, args);
        System.out.println("Hello World!");
    }

}
