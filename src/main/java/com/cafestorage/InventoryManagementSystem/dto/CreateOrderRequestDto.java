package com.cafestorage.InventoryManagementSystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDto {
    @NotEmpty
    @Valid
    private List<OrderItemRequestDto> items;
}