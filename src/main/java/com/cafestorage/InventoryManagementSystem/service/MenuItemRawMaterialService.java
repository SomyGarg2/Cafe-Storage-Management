package com.cafestorage.InventoryManagementSystem.service;

import com.cafestorage.InventoryManagementSystem.dto.MenuItemRawMaterialDto;
import com.cafestorage.InventoryManagementSystem.entity.MenuItem;
import com.cafestorage.InventoryManagementSystem.entity.MenuItemRawMaterial;
import com.cafestorage.InventoryManagementSystem.entity.RawMaterial;
import com.cafestorage.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.cafestorage.InventoryManagementSystem.repository.MenuItemRawMaterialRepository;
import com.cafestorage.InventoryManagementSystem.repository.MenuItemRepository;
import com.cafestorage.InventoryManagementSystem.repository.RawMaterialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemRawMaterialService {

    private final MenuItemRawMaterialRepository menuItemRawMaterialRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    public MenuItemRawMaterialService(MenuItemRawMaterialRepository menuItemRawMaterialRepository,
                                      RawMaterialRepository rawMaterialRepository,
                                      MenuItemRepository menuItemRepository,
                                      ModelMapper modelMapper) {
        this.menuItemRawMaterialRepository = menuItemRawMaterialRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.menuItemRepository = menuItemRepository;
        this.modelMapper = modelMapper;
    }


    public MenuItemRawMaterialDto addRecipe(MenuItemRawMaterialDto dto){
        if (menuItemRawMaterialRepository
                .existsByMenuItemIdAndRawMaterialId(dto.getMenuItemId(), dto.getRawMaterialId())) {
            throw new IllegalStateException("Raw material already added to menu item");
        }

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        MenuItemRawMaterial mapping = new MenuItemRawMaterial();
        mapping.setMenuItem(menuItem);
        mapping.setRawMaterial(rawMaterial);
        mapping.setQuantityRequired(dto.getQuantityRequired());

        return modelMapper.map(
                menuItemRawMaterialRepository.save(mapping),
                MenuItemRawMaterialDto.class
        );
    }

    public MenuItemRawMaterialDto updateRecipe(Long id, Double quantityRequired) {

        MenuItemRawMaterial mapping = menuItemRawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        mapping.setQuantityRequired(quantityRequired);

        return modelMapper.map(
                menuItemRawMaterialRepository.save(mapping),
                MenuItemRawMaterialDto.class
        );
    }

    public void removeRawMaterial(Long id) {

        if (!menuItemRawMaterialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found");
        }

        menuItemRawMaterialRepository.deleteById(id);
    }

    public List<MenuItemRawMaterialDto> getRecipeByMenuItem(Long menuItemId) { // get all raw materials for a menu item

        return menuItemRawMaterialRepository.findByMenuItemId(menuItemId)
                .stream()
                .map(mapping -> modelMapper.map(mapping, MenuItemRawMaterialDto.class))
                .toList();
    }


}
