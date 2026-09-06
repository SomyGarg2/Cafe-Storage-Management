package com.cafestorage.InventoryManagementSystem.dto;


import com.cafestorage.InventoryManagementSystem.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    @NotNull
    private LocalDateTime orderDate;


    @NotNull
    private OrderStatus status;

}