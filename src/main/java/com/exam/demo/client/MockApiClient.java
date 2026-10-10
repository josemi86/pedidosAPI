package com.exam.demo.client;

import com.exam.demo.model.Item;
import com.exam.demo.model.Order;
import com.exam.demo.client.dto.ItemDto;
import com.exam.demo.client.dto.PedidoDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class MockApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String PEDIDOS_URL = "https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/pedidos";
    private final String ITEMS_URL = "https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/items";

    public List<Order> fetchUserOrders(String userId) {
        List<Order> orders= new ArrayList<>();
        List<PedidoDto> pedidos = fetchOrders();
        List<ItemDto> items = fetchItems();
        for (PedidoDto pedido : pedidos) {
            if (pedido.userId().equals(userId)) {
                Order order = new Order();
                order.setOrderRef(pedido.orderRef());
                order.setChannel(pedido.canal());
                order.setItems(fetchOrderItems(pedido.items(), items));
                orders.add(order);
            }
        }
        return orders;
    }

    private List<Item> fetchOrderItems(List<String> itemsPedido, List<ItemDto> items) {
        List<Item> orderItems = new ArrayList<>();
        for (String itemPedido : itemsPedido) {
            for (ItemDto item : items) {
                if (itemPedido.equals(item.itemId())) {
                    Item orderItem = new Item();
                    orderItem.setItemId(item.itemId());
                    orderItem.setQuantity(item.quantity());
                    orderItem.setDeliveryStatus(item.deliveryStatus());
                    orderItems.add(orderItem);
                }
            }
        }
        return orderItems;
    }

    public List<PedidoDto> fetchOrders() {
        PedidoDto[] rawPedidos = restTemplate.getForObject(PEDIDOS_URL, PedidoDto[].class);
        return rawPedidos != null ? Arrays.asList(rawPedidos) : Collections.emptyList();
    }

    public List<ItemDto> fetchItems() {
        ItemDto[] rawItems = restTemplate.getForObject(ITEMS_URL, ItemDto[].class);
        return rawItems != null ? Arrays.asList(rawItems) : Collections.emptyList();
    }
}
