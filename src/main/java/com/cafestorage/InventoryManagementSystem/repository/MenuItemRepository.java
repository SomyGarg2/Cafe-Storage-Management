package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.entity.MenuItem;
import com.cafestorage.InventoryManagementSystem.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// MenuItemRepository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByName(String name);

    default MenuItem resolve(Long id, String name) {
        if (id != null) {
            return findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        }
        if (name != null) {
            return findByName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        }
        throw new IllegalArgumentException("Either menuItemId or menuItemName must be provided");
    }
}