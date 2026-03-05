package com.cafestorage.InventoryManagementSystem.controller;

import com.cafestorage.InventoryManagementSystem.dto.CreateOrderRequestDto;
import com.cafestorage.InventoryManagementSystem.dto.OrderDto;
import com.cafestorage.InventoryManagementSystem.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderRequestDto request) {
        return orderService.createOrder(request);
    }


    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }
}
