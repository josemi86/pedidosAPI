package com.exam.demo.service;

import com.exam.demo.client.MockApiClient;
import com.exam.demo.model.dto.ItemResponseDto;
import com.exam.demo.model.dto.OrderResponseDto;
import com.exam.demo.client.dto.ItemDto;
import com.exam.demo.client.dto.PedidoDto;
import com.exam.demo.util.TextNormalizer;
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

        List<PedidoDto> pedidos = mockApiClient.fetchOrders();
        List<ItemDto> items = mockApiClient.fetchItems();

        return pedidos.stream()
                .map(pedido -> {
                    List<ItemResponseDto> orderItems = new ArrayList<>();
                    ItemResponseDto orderItem;
                    for (ItemDto item : items){
                        if(pedido.items().contains(item.itemId())){
                            orderItem = new ItemResponseDto();
                            orderItem.setDisplayName(item.displayName());
                            orderItem.setDeliveryStatus(item.deliveryStatus());
                            orderItem.setQuantity(item.quantity());
                            orderItem.setItemId(item.itemId());
                            orderItems.add(orderItem);
                        }
                    }
                    OrderResponseDto order = new OrderResponseDto();
                    order.setOrderRef(pedido.orderRef());
                    order.setOrderStatus(pedido.orderStatus());
                    order.setStoreName(pedido.storeName());
                    order.setChannel(pedido.canal());
                    order.setItems(orderItems);
                    return order;
                })
                .filter(order -> matchesCriteria(order, query))
                .collect(Collectors.toList());
    }

    private boolean matchesCriteria(OrderResponseDto order, String query) {
        if (TextNormalizer.flexibleMatches(order.getOrderRef(), query)) return true;
        if (TextNormalizer.flexibleMatches(order.getOrderStatus(), query)) return true;
        if (TextNormalizer.flexibleMatches(order.getStoreName(), query)) return true;
        return order.getItems().stream()
                .anyMatch(itemName -> TextNormalizer.flexibleMatches(itemName.getDisplayName(), query));
    }
}