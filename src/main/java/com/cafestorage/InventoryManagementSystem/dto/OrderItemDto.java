package com.cafestorage.InventoryManagementSystem.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;

    @NotNull
    private Long orderId;

    @NotNull
    private Long menuItemId;

    @NotNull
    private Integer quantity;

    @NotNull
    @Positive
    private Double price;

}
