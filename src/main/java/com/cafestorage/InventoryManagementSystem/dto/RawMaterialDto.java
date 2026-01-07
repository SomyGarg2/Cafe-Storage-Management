package com.cafestorage.InventoryManagementSystem.dto;


import jakarta.validation.constraints.NotBlank;
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
public class RawMaterialDto {


    private Long id;

    @NotBlank
    private String name;

    private String category;

    @NotBlank
    private String unit;

    @NotNull
    @Positive
    private Double quantity;

    @NotNull
    @Positive
    private Double minStockLevel;

    @NotNull
    private Long supplierId;

    private Boolean active;


}
