package com.cafestorage.InventoryManagementSystem.controller;

import com.cafestorage.InventoryManagementSystem.dto.MenuItemDto;
import com.cafestorage.InventoryManagementSystem.service.MenuItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public MenuItemDto addItem(@RequestBody MenuItemDto menuItemDto) {
        return menuItemService.addItem(menuItemDto);
    }

    @PutMapping(path = "/{id}")
    public MenuItemDto updateItem(@RequestBody MenuItemDto menuItemDto,@PathVariable Long id) {
        return menuItemService.updateItem(menuItemDto, id);
    }

    @PutMapping(path = "/{id}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableMenuItem(@PathVariable Long id) {
        menuItemService.enableMenuItem(id);
    }

    @PutMapping(path = "/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableMenuItem(@PathVariable Long id) {
        menuItemService.disableMenuItem(id);
    }
}
