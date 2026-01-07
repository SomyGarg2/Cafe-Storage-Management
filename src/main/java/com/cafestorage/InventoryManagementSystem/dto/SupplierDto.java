package com.cafestorage.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String contact;

    private String email;

    private String address;

    private Boolean status;
}
