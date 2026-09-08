package com.cafestorage.InventoryManagementSystem.dto;

import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Long menuItemId;
    private String menuItemName;
    private Integer quantity;
}
