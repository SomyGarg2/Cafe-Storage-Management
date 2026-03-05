package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.dto.MonthlyPurchaseSummaryDto;
import com.cafestorage.InventoryManagementSystem.entity.StockInward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockInwardRepository extends JpaRepository<StockInward, Long> {

    @Query("""
        SELECT new com.cafestorage.InventoryManagementSystem.dto.MonthlyPurchaseSummaryDto(
            s.rawMaterial.name,
            s.rawMaterial.unit,
            SUM(s.quantity),
            SUM(s.totalPrice)
        )
        FROM StockInward s
        WHERE FUNCTION('YEAR', s.purchaseDate) = :year
        AND FUNCTION('MONTH', s.purchaseDate) = :month
        GROUP BY s.rawMaterial.name, s.rawMaterial.unit
        ORDER BY s.rawMaterial.name ASC
    """)
    List<MonthlyPurchaseSummaryDto> findMonthlyPurchaseSummary(
            @Param("year") int year,
            @Param("month") int month
    );
}
