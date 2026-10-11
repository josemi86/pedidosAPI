package com.exam.model.dto.mockapi;

import java.util.List;

public record MockApiPedidoDto(
        String orderRef,
        String userId,
        String canal,
        String orderStatus,
        List<String> items,
        String storeName
) {}