package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // OrderItemRepository
    List<OrderItem> findByOrderId(Long orderId);
}
