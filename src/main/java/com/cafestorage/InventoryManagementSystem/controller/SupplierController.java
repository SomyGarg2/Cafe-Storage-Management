package com.cafestorage.InventoryManagementSystem.controller;

import com.cafestorage.InventoryManagementSystem.dto.SupplierDto;
import com.cafestorage.InventoryManagementSystem.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService supplierService;
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public SupplierDto addSupplier(@RequestBody SupplierDto supplierDto) {
        return supplierService.addSupplier(supplierDto);
    }

    @GetMapping(path = "/{id}")
    public SupplierDto getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @GetMapping
    public List<SupplierDto> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @PutMapping(path = "/{id}")
    public SupplierDto updateSupplier(@PathVariable Long id, @RequestBody SupplierDto supplierDto){
        return supplierService.updateSupplier(id, supplierDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }

}
