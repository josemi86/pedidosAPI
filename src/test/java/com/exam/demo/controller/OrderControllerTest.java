package com.exam.demo.controller;

import com.exam.demo.model.dto.OrderResponseDto;
import com.exam.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void search_WithValidQuery_ShouldReturn200AndOrderList() throws Exception {
        String validQuery = "order";
        OrderResponseDto order1 = new OrderResponseDto("ORD-001", Collections.emptyList(), "channel1", "orderStatus1", "storeName1");
        OrderResponseDto order2 = new OrderResponseDto("ORD-002", Collections.emptyList(), "channel2", "orderStatus2", "storeName2");
        List<OrderResponseDto> mockResponse = List.of(order1, order2);
        when(orderService.searchOrders(validQuery)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/orders/search")
                        .param("query", validQuery)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].orderRef").value("ORD-001"))
                .andExpect(jsonPath("$[1].orderRef").value("ORD-002"));

        verify(orderService, times(1)).searchOrders(validQuery);
    }

    @Test
    void search_WithShortQuery_ShouldReturn400BadRequest() throws Exception {
        String shortQuery = "A";

        mockMvc.perform(get("/api/v1/orders/search")
                        .param("query", shortQuery)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(orderService, never()).searchOrders(anyString());
    }

    @Test
    void search_WithBlankQuery_ShouldReturn400BadRequest() throws Exception {
        String blankQuery = "   ";

        mockMvc.perform(get("/api/v1/orders/search")
                        .param("query", blankQuery)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(orderService, never()).searchOrders(anyString());
    }

    @Test
    void search_MissingQueryParam_ShouldReturn400BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/orders/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(orderService, never()).searchOrders(anyString());
    }
}