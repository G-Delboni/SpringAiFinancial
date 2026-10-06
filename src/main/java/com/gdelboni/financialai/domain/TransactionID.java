package com.gdelboni.financialai.domain;

import java.util.UUID;

public record TransactionID(UUID uuid) {
    public TransactionID() {
        this(UUID.randomUUID());
    }
}
