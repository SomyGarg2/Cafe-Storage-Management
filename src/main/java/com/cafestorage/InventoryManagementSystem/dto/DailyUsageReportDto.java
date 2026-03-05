package com.cafestorage.InventoryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyUsageReportDto {

    private String rawMaterialName;  // e.g. "Milk"
    private String unit;             // e.g. "litres"
    private Double totalQuantityUsed; // total used on that day

}
