package com.exam.demo.client;

import com.exam.demo.client.dto.ItemDto;
import com.exam.demo.client.dto.PedidoDto;
import com.exam.demo.model.Item;
import com.exam.demo.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MockApiClient mockApiClient;

    private final String mockPedidosUrl = "http://mock.com";
    private final String mockItemsUrl = "http://mock.com";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mockApiClient, "PEDIDOS_URL", mockPedidosUrl);
        ReflectionTestUtils.setField(mockApiClient, "ITEMS_URL", mockItemsUrl);
    }

    @Test
    void fetchOrders_WhenApiReturnsData_ShouldReturnList() {
        PedidoDto[] mockRawPedidos = {
                new PedidoDto("order1", "user1", "channel1", "status1", Collections.emptyList(), "store1")
        };
        when(restTemplate.getForObject(mockPedidosUrl, PedidoDto[].class)).thenReturn(mockRawPedidos);

        List<PedidoDto> result = mockApiClient.fetchOrders();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).userId()).isEqualTo("user1");
    }

    @Test
    void fetchOrders_WhenApiReturnsNull_ShouldReturnEmptyList() {
        when(restTemplate.getForObject(mockPedidosUrl, PedidoDto[].class)).thenReturn(null);

        List<PedidoDto> result = mockApiClient.fetchOrders();

        assertThat(result).isEmpty();
    }

    @Test
    void fetchItems_WhenApiReturnsData_ShouldReturnList() {
        ItemDto[] mockRawItems = {
                new ItemDto("item1", 2, "DELIVERED", "STATUS1")
        };
        when(restTemplate.getForObject(mockItemsUrl, ItemDto[].class)).thenReturn(mockRawItems);

        List<ItemDto> result = mockApiClient.fetchItems();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).itemId()).isEqualTo("item1");
    }

    @Test
    void fetchUserOrders_WhenUserHasOrders_ShouldFilterAndMapCorrectly() {
        String userId = "user1";

        PedidoDto[] mockRawPedidos = {
                new PedidoDto("order1", "user1", "channel1", "status1", List.of("item1"), "store1"),
                new PedidoDto("order2", "user2", "channel2", "status2", List.of("item2"), "store2")
        };
        ItemDto[] mockRawItems = {
                new ItemDto("item1", 2, "PENDING", "STATUS1"),
                new ItemDto("item2", 4, "DELIVERED", "STATUS2")
        };
        when(restTemplate.getForObject(mockPedidosUrl, PedidoDto[].class)).thenReturn(mockRawPedidos);
        when(restTemplate.getForObject(mockItemsUrl, ItemDto[].class)).thenReturn(mockRawItems);

        List<Order> result = mockApiClient.fetchUserOrders(userId);

        assertThat(result).hasSize(1);
        Order filteredOrder = result.get(0);
        assertThat(filteredOrder.orderRef()).isEqualTo("order1");
        assertThat(filteredOrder.channel()).isEqualTo("channel1");

        List<Item> orderItems = filteredOrder.items();
        assertThat(orderItems).hasSize(1);
        assertThat(orderItems.get(0).itemId()).isEqualTo("item1");
        assertThat(orderItems.get(0).quantity()).isEqualTo(2);
        assertThat(orderItems.get(0).deliveryStatus()).isEqualTo("STATUS1");
    }

    @Test
    void fetchUserOrders_WhenUserHasNoOrders_ShouldReturnEmptyList() {
        String userId = "user_without_orders";
        PedidoDto[] mockRawPedidos = {
                new PedidoDto("order1", "user1", "channel1", "status1", Collections.emptyList(), "store1")
        };
        ItemDto[] mockRawItems = {};
        when(restTemplate.getForObject(mockPedidosUrl, PedidoDto[].class)).thenReturn(mockRawPedidos);
        when(restTemplate.getForObject(mockItemsUrl, ItemDto[].class)).thenReturn(mockRawItems);

        List<Order> result = mockApiClient.fetchUserOrders(userId);

        assertThat(result).isEmpty();
    }
}