package com.cafestorage.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotNull
    private Long menuItemId;

    @NotNull
    @Positive
    private Integer quantity;
}