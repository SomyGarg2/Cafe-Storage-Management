package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.dto.DailyUsageReportDto;
import com.cafestorage.InventoryManagementSystem.entity.StockOutward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StockOutwardRepository extends JpaRepository<StockOutward, Long> {
    List<StockOutward> findByOrderId(Long orderId);

    @Query("""
        SELECT new com.cafestorage.InventoryManagementSystem.dto.DailyUsageReportDto(
            s.rawMaterial.name,
            s.rawMaterial.unit,
            SUM(s.quantityUsed)
        )
        FROM StockOutward s
        WHERE CAST(s.usedDate AS LocalDate) = :date
        GROUP BY s.rawMaterial.name, s.rawMaterial.unit
        ORDER BY s.rawMaterial.name ASC
    """)
    List<DailyUsageReportDto> findDailyUsageReport(@Param("date") LocalDate date);
}
