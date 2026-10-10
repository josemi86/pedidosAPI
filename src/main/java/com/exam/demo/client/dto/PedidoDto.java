package com.exam.demo.client.dto;

import java.util.List;

public record PedidoDto(
        String orderRef,
        String userId,
        String canal,
        String orderStatus,
        List<String> items,
        String storeName
) {}