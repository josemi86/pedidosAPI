package com.exam.demo.controller;

import com.exam.demo.model.Order;
import com.exam.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderSearchService) {
        this.orderService = orderSearchService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> search(@RequestParam("q") String query) {
        if (query == null || query.trim().length() < 2) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}