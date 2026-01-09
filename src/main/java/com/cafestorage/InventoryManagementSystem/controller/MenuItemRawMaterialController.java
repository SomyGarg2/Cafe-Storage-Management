package com.cafestorage.InventoryManagementSystem.controller;

import com.cafestorage.InventoryManagementSystem.dto.MenuItemRawMaterialDto;
import com.cafestorage.InventoryManagementSystem.service.MenuItemRawMaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-item-raw-materials")
public class MenuItemRawMaterialController {

    private final MenuItemRawMaterialService menuItemRawMaterialService;

    public MenuItemRawMaterialController(MenuItemRawMaterialService menuItemRawMaterialService) {
        this.menuItemRawMaterialService = menuItemRawMaterialService;
    }

    @PostMapping
    public MenuItemRawMaterialDto addRecipe(@RequestBody MenuItemRawMaterialDto dto) {
        return menuItemRawMaterialService.addRecipe(dto);
    }

    @PutMapping("/{id}")
    public MenuItemRawMaterialDto updateRecipe(@PathVariable Long id,
                                               @RequestParam Double quantityRequired) {
        return menuItemRawMaterialService.updateRecipe(id, quantityRequired);
    }

    @DeleteMapping("/{id}")
    public void removeRawMaterial(@PathVariable Long id) {
        menuItemRawMaterialService.removeRawMaterial(id);
    }


    @GetMapping("/menu-item/{menuItemId}")
    public List<MenuItemRawMaterialDto> getRecipeByMenuItem(
            @PathVariable Long menuItemId) {
        return menuItemRawMaterialService.getRecipeByMenuItem(menuItemId);
    }
}
