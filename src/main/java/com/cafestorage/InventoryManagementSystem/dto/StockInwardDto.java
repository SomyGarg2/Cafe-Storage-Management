package com.cafestorage.InventoryManagementSystem.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockInwardDto {
    private Long id;

    @NotNull
    private Long rawMaterialId;

    @NotNull
    private Long supplierId;

    @NotNull
    @Positive
    private Double quantity;

    @NotNull
    @Positive
    private Double unitPrice;

    private Double totalPrice;

    private LocalDateTime purchaseDate;
}
