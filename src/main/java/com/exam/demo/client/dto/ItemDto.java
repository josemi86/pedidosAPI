package com.exam.demo.client.dto;

public record ItemDto(
        String itemId,
        int quantity,
        String displayName,
        String deliveryStatus
) {}