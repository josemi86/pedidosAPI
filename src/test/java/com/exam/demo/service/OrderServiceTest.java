package com.exam.demo.service;

import com.exam.demo.client.MockApiClient;
import com.exam.demo.model.dto.mockapi.MockApiItemDto;
import com.exam.demo.model.dto.mockapi.MockApiPedidoDto;
import com.exam.demo.model.dto.ItemResponseDto;
import com.exam.demo.model.dto.OrderResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private MockApiClient mockApiClient;

    @InjectMocks
    private OrderService orderService;

    private List<MockApiPedidoDto> mockPedidos;
    private List<MockApiItemDto> mockItems;

    @BeforeEach
    void setUp() {
        mockPedidos = List.of(
                new MockApiPedidoDto("order1", "user1", "channel1", "status1", List.of("item2", "item3"), "store1"),
                new MockApiPedidoDto("order2", "user2", "channel2", "status2", List.of("item1"), "store2")
        );

        mockItems = List.of(
                new MockApiItemDto("item1", 1, "Laptop Dell XPS", "DELIVERED"),
                new MockApiItemDto("item2", 2, "Mouse Inalámbrico", "SHIPPED"),
                new MockApiItemDto("item3", 3, "Smartphone Samsung", "PROCESSING")
        );
    }

    @Test
    void searchOrders_ShouldCorrectlyMapPedidosToOrderResponseDto() {
        String query = "Mouse";
        when(mockApiClient.fetchOrders()).thenReturn(mockPedidos);
        when(mockApiClient.fetchItems()).thenReturn(mockItems);

        List<OrderResponseDto> result = orderService.searchOrders(query);

        assertThat(result).hasSize(1);
        OrderResponseDto mappedOrder = result.get(0);

        assertThat(mappedOrder.orderRef()).isEqualTo("order1");
        assertThat(mappedOrder.channel()).isEqualTo("channel1");
        assertThat(mappedOrder.orderStatus()).isEqualTo("status1");
        assertThat(mappedOrder.storeName()).isEqualTo("store1");

        List<ItemResponseDto> orderItems = mappedOrder.items();
        assertThat(orderItems).hasSize(2);
        assertThat(orderItems.get(0).itemId()).isEqualTo("item2");
        assertThat(orderItems.get(0).displayName()).isEqualTo("Mouse Inalámbrico");
        assertThat(orderItems.get(1).itemId()).isEqualTo("item3");
        assertThat(orderItems.get(1).displayName()).isEqualTo("Smartphone Samsung");
    }

    @Test
    void searchOrders_WhenQueryMatchesOrderRef_ShouldReturnOrder() {
        String query = "LAPTOP";
        when(mockApiClient.fetchOrders()).thenReturn(mockPedidos);
        when(mockApiClient.fetchItems()).thenReturn(mockItems);

        List<OrderResponseDto> result = orderService.searchOrders(query);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).orderRef()).isEqualTo("order2");
    }

    @Test
    void searchOrders_WhenQueryMatchesOrderStatus_ShouldReturnOrder() {
        String query = "Mouse";
        when(mockApiClient.fetchOrders()).thenReturn(mockPedidos);
        when(mockApiClient.fetchItems()).thenReturn(mockItems);

        List<OrderResponseDto> result = orderService.searchOrders(query);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).orderRef()).isEqualTo("order1");
    }

    @Test
    void searchOrders_WhenQueryMatchesStoreName_ShouldReturnOrder() {
        String query = "store1";
        when(mockApiClient.fetchOrders()).thenReturn(mockPedidos);
        when(mockApiClient.fetchItems()).thenReturn(mockItems);

        List<OrderResponseDto> result = orderService.searchOrders(query);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).storeName()).isEqualTo("store1");
        assertThat(result.get(1).storeName()).isEqualTo("store2");
    }

    @Test
    void searchOrders_WhenQueryMatchesItemDisplayName_ShouldReturnOrder() {
        String query = "Samsung";
        when(mockApiClient.fetchOrders()).thenReturn(mockPedidos);
        when(mockApiClient.fetchItems()).thenReturn(mockItems);

        List<OrderResponseDto> result = orderService.searchOrders(query);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).orderRef()).isEqualTo("order1");
    }

    @Test
    void searchOrders_WhenNoOrdersMatchQuery_ShouldReturnEmptyList() {
        String query = "NonExistentCriteria";
        when(mockApiClient.fetchOrders()).thenReturn(mockPedidos);
        when(mockApiClient.fetchItems()).thenReturn(mockItems);

        List<OrderResponseDto> result = orderService.searchOrders(query);

        assertThat(result).isEmpty();
    }

    @Test
    void searchOrders_WhenApiReturnsNoOrders_ShouldReturnEmptyList() {
        String query = "Laptop";
        when(mockApiClient.fetchOrders()).thenReturn(Collections.emptyList());
        when(mockApiClient.fetchItems()).thenReturn(mockItems);

        List<OrderResponseDto> result = orderService.searchOrders(query);

        assertThat(result).isEmpty();
        verify(mockApiClient, times(1)).fetchOrders();
        verify(mockApiClient, times(1)).fetchItems();
    }
}