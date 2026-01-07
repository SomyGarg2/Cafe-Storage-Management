package com.cafestorage.InventoryManagementSystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDto {
    private List<OrderItemRequestDto> items;
}

