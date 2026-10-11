package com.exam.demo.client;

import com.exam.demo.model.Item;
import com.exam.demo.model.Order;
import com.exam.demo.client.dto.ItemDto;
import com.exam.demo.client.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

@Component
public class MockApiClient {

    private final RestClient restClient;

    @Value("${mockapi.pedidos.url}")
    private String PEDIDOS_URL;

    @Value("${mockapi.items.url}")
    private String ITEMS_URL;

    public MockApiClient(RestClient restClient) {
        this.restClient = restClient;
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
        return restClient.get()
                .uri(PEDIDOS_URL)
                .retrieve()
                .body(new ParameterizedTypeReference<List<PedidoDto>>() {});
    }

    public List<ItemDto> fetchItems() {
        return restClient.get()
                .uri(ITEMS_URL)
                .retrieve()
                .body(new ParameterizedTypeReference<List<ItemDto>>() {});
    }

}
