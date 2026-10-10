package com.exam.demo.model.http;

public record ItemDTO(
        String itemId,
        int quantity,
        String displayName,
        String deliveryStatus
) {}