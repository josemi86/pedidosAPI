package com.exam.model;

public record Item (
        String itemId,
        Integer quantity,
        String deliveryStatus
){}