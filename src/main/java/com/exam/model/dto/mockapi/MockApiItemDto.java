package com.exam.model.dto.mockapi;

public record MockApiItemDto(
        String itemId,
        int quantity,
        String displayName,
        String deliveryStatus
) {}