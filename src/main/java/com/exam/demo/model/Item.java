package com.exam.demo.model;

public record Item (
        String itemId,
        Integer quantity,
        String deliveryStatus
){}