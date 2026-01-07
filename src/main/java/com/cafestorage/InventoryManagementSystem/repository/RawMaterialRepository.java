package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    boolean existsByName(String name);
    @Query("""
       SELECT r FROM RawMaterial r
       WHERE r.quantity <= r.minStockLevel
       AND r.active = true
       """)
    List<RawMaterial> findLowStockMaterials();

}
