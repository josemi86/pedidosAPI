package com.exam.demo.model.dto;

import java.util.List;

public record OrderResponseDto (
        String orderRef,
        List<ItemResponseDto> items,
        String channel,
        String orderStatus,
        String storeName
) {}
