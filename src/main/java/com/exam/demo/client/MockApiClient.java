package com.exam.demo.client;

import com.exam.demo.model.Item;
import com.exam.demo.model.Order;
import com.exam.demo.client.dto.ItemDto;
import com.exam.demo.client.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class MockApiClient {

    private final RestTemplate restTemplate;

    @Value("${pedidos.url}")
    private String PEDIDOS_URL;

    @Value("${items.url}")
    private String ITEMS_URL;

    public MockApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Order> fetchUserOrders(String userId) {
        List<PedidoDto> pedidos = fetchOrders();
        List<ItemDto> items = fetchItems();
        return pedidos.stream()
                .filter(pedido -> pedido.userId().equals(userId))
                .map(pedido -> new Order(pedido.orderRef(), fetchOrderItems(pedido.items(), items), pedido.canal()))
                .toList();
    }

    private List<Item> fetchOrderItems(List<String> itemsPedido, List<ItemDto> items) {
        return itemsPedido.stream()
                .flatMap(itemPedido -> items.stream()
                        .filter(item -> itemPedido.equals(item.itemId())))
                .map(item -> new Item(item.itemId(), item.quantity(), item.deliveryStatus()))
                .toList();
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
