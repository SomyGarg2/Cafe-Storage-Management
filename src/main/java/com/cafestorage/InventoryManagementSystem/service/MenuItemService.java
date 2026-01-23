package com.cafestorage.InventoryManagementSystem.service;

import com.cafestorage.InventoryManagementSystem.dto.MenuItemDto;
import com.cafestorage.InventoryManagementSystem.entity.MenuItem;
import com.cafestorage.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.cafestorage.InventoryManagementSystem.repository.MenuItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    public MenuItemService(MenuItemRepository menuItemRepository, ModelMapper modelMapper) {
        this.menuItemRepository = menuItemRepository;
        this.modelMapper = modelMapper;
    }


    public MenuItemDto addItem(MenuItemDto menuItemDto){
        MenuItem menuItem = modelMapper.map(menuItemDto, MenuItem.class);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return modelMapper.map(savedMenuItem, MenuItemDto.class);
    }

    public MenuItemDto updateItem(MenuItemDto menuItemDto, Long id){
        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("item not found"));

        existingMenuItem.setName(menuItemDto.getName());
        existingMenuItem.setDescription(menuItemDto.getDescription());
        existingMenuItem.setPrice(menuItemDto.getPrice());

        MenuItem savedUpdatedItem = menuItemRepository.save(existingMenuItem);

        return modelMapper.map(savedUpdatedItem,MenuItemDto.class);
    }

    public void enableMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        menuItem.setAvailable(true);
        menuItemRepository.save(menuItem);
    }

    public void disableMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        menuItem.setAvailable(false);
        menuItemRepository.save(menuItem);
    }


}
