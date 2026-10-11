package com.exam.demo.model.dto.mockapi;

public record MockApiItemDto(
        String itemId,
        int quantity,
        String displayName,
        String deliveryStatus
) {}