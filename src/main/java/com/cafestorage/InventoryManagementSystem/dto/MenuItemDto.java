package com.cafestorage.InventoryManagementSystem.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemDto {
    private Long id;

    @NotBlank
    private String name;
    private String description;

    @NotBlank
    @Positive
    private Double price;


}
