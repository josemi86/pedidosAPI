package com.exam.controller;

import com.exam.model.dto.OrderResponseDto;
import com.exam.service.OrderService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    public ResponseEntity<List<OrderResponseDto>> search(@RequestParam @NotBlank @Size(min = 2) String query) {
        return ResponseEntity.ok(orderService.searchOrders(query));
    }
}