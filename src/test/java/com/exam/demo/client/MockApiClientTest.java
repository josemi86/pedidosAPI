package com.exam.demo.client;

import com.exam.demo.client.dto.ItemDto;
import com.exam.demo.client.dto.PedidoDto;
import com.exam.demo.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class MockApiClientUnitTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private MockApiClient mockApiClient;

    private List<PedidoDto> mockPedidos;
    private List<ItemDto> mockItems;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mockApiClient, "PEDIDOS_URL", "http://api.com");
        ReflectionTestUtils.setField(mockApiClient, "ITEMS_URL", "http://api.com");

        mockPedidos = List.of(
                new PedidoDto("order1", "user1", "channel1", "status1", List.of("item1"), "store1"),
                new PedidoDto("order2", "user2", "channel2", "status2", List.of("item2"), "store2")
        );

        mockItems = List.of(
                new ItemDto("item1", 2, "displayName1", "deliveryStatus1"),
                new ItemDto("item2", 4, "displayName2", "deliveryStatus2")
        );
    }

    @SuppressWarnings("unchecked")
    private void mockRestClientChain(Object responseBody) {
        Mockito.lenient().when(restClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.lenient().when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        Mockito.lenient().when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.lenient().when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(responseBody);
    }

    @Test
    void fetchUserOrders_WhenUserHasOrders_ShouldFilterAndAssembleOrders() {
        mockRestClientChain(mockPedidos);
        Mockito.lenient().when(responseSpec.body(any(ParameterizedTypeReference.class)))
                .thenReturn(mockPedidos)
                .thenReturn(mockItems);

        List<Order> result = mockApiClient.fetchUserOrders("user1");

        assertThat(result).hasSize(1);
        Order order = result.get(0);
        assertThat(order.orderRef()).isEqualTo("order1");
        assertThat(order.channel()).isEqualTo("channel1");
        assertThat(order.items()).hasSize(1);
        assertThat(order.items().get(0).itemId()).isEqualTo("item1");
        assertThat(order.items().get(0).deliveryStatus()).isEqualTo("deliveryStatus1");
    }

    @Test
    void fetchUserOrders_WhenUserDoesNotExist_ShouldReturnEmptyList() {
        mockRestClientChain(mockPedidos);
        Mockito.lenient().when(responseSpec.body(any(ParameterizedTypeReference.class)))
                .thenReturn(mockPedidos)
                .thenReturn(mockItems);

        List<Order> result = mockApiClient.fetchUserOrders("non_existent_user");

        assertThat(result).isEmpty();
    }
}
