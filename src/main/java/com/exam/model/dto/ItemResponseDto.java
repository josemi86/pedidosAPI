package com.exam.model.dto;

public record ItemResponseDto (
        String itemId,
        Integer quantity,
        String deliveryStatus,
        String displayName
) {}
