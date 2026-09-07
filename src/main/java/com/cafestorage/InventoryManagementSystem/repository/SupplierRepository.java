package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsById(Long id);
    java.util.Optional<Supplier> findByName(String name);
}