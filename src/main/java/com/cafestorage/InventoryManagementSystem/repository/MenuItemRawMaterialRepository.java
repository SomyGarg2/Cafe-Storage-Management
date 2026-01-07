package com.cafestorage.InventoryManagementSystem.repository;

import com.cafestorage.InventoryManagementSystem.entity.MenuItemRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRawMaterialRepository extends JpaRepository<MenuItemRawMaterial, Long> {
    List<MenuItemRawMaterial> findByMenuItemId(Long menuItemId);
    boolean existsByMenuItemIdAndRawMaterialId(Long menuItemId, Long rawMaterialId);


}
