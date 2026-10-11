package com.exam.model.dto;

import java.util.List;

public record OrderResponseDto (
        String orderRef,
        List<ItemResponseDto> items,
        String channel,
        String orderStatus,
        String storeName
) {}
