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
public class MenuItemRawMaterialDto {
    private Long id;

    private Long menuItemId;
    private String menuItemName;

    private Long rawMaterialId;
    private String rawMaterialName;

    @NotNull @Positive
    private Double quantityRequired;
}
