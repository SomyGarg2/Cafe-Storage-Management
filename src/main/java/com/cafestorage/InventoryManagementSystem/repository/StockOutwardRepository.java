package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.entity.StockOutward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockOutwardRepository extends JpaRepository<StockOutward, Long> {
    List<StockOutward> findByOrderId(Long orderId);
}
