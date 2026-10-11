package com.exam.service;

import com.exam.client.MockApiClient;
import com.exam.model.dto.ItemResponseDto;
import com.exam.model.dto.OrderResponseDto;
import com.exam.model.dto.mockapi.MockApiItemDto;
import com.exam.model.dto.mockapi.MockApiPedidoDto;
import com.exam.util.TextNormalizer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final MockApiClient mockApiClient;

    public OrderService(MockApiClient mockApiClient) {
        this.mockApiClient = mockApiClient;
    }

    public List<OrderResponseDto> searchOrders(String query) {
        List<MockApiPedidoDto> pedidos = mockApiClient.fetchOrders();
        List<MockApiItemDto> items = mockApiClient.fetchItems();
        return pedidos.stream()
                .map(pedido -> {
                    List<ItemResponseDto> orderItems = items.stream()
                            .filter(item -> pedido.items().contains(item.itemId()))
                            .map(item -> new ItemResponseDto(
                                    item.itemId(), item.quantity(), item.deliveryStatus(), item.displayName()))
                            .toList();
                    return new OrderResponseDto(
                            pedido.orderRef(), orderItems, pedido.canal(), pedido.orderStatus(), pedido.storeName());
                })
                .filter(order -> matchesCriteria(order, query))
                .collect(Collectors.toList());
    }

    private boolean matchesCriteria(OrderResponseDto order, String query) {
        if (TextNormalizer.flexibleMatches(order.orderRef(), query)) return true;
        if (TextNormalizer.flexibleMatches(order.orderStatus(), query)) return true;
        if (TextNormalizer.flexibleMatches(order.storeName(), query)) return true;
        return order.items().stream()
                .anyMatch(itemName -> TextNormalizer.flexibleMatches(itemName.displayName(), query));
    }
}