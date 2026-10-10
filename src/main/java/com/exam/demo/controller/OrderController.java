package com.exam.demo.controller;

import com.exam.demo.model.dto.OrderResponseDto;
import com.exam.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderResponseDto>> search(@RequestParam("text") String query) {
        if (query == null || query.trim().length() < 2) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(orderService.searchOrders(query));
    }
}