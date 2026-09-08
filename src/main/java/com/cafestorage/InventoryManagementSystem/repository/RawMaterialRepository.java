package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.entity.RawMaterial;
import com.cafestorage.InventoryManagementSystem.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    boolean existsByName(String name);
    java.util.Optional<RawMaterial> findByName(String name);
    @Query("""
       SELECT r FROM RawMaterial r
       WHERE r.quantity <= r.minStockLevel
       """)
    List<RawMaterial> findLowStockMaterials();

    default RawMaterial resolve(Long id, String name) {
        if (id != null) {
            return findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));
        }
        if (name != null) {
            return findByName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));
        }
        throw new IllegalArgumentException("Either rawMaterialId or rawMaterialName must be provided");
    }

}