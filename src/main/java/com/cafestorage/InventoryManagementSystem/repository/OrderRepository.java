package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.entity.Order;
import com.cafestorage.InventoryManagementSystem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
