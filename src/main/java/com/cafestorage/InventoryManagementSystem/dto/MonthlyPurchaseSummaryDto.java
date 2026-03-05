package com.cafestorage.InventoryManagementSystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyPurchaseSummaryDto {

    private String rawMaterialName;       // e.g. "Milk"
    private String unit;                  // e.g. "litres"
    private Double totalQuantityPurchased; // total quantity bought that month
    private Double totalAmountSpent;       // total money spent that month

}
