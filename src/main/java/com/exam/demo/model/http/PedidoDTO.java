package com.exam.demo.model.http;

import java.util.List;

public record PedidoDTO(
        String orderRef,
        String userId,
        String canal,
        String orderStatus,
        List<String> items,
        String storeName
) {}