package com.cafestorage.InventoryManagementSystem.controller;

import com.cafestorage.InventoryManagementSystem.dto.RawMaterialDto;
import com.cafestorage.InventoryManagementSystem.service.RawMaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")

public class RawMaterialController {

    private final RawMaterialService rawMaterialService;
    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }





    @PostMapping
    public RawMaterialDto addRawMaterial(@RequestBody RawMaterialDto rawMaterialDto) {
        return rawMaterialService.addRawMaterial(rawMaterialDto);
    }

    @PutMapping(path = "/{id}")
    public RawMaterialDto updateRawMaterial(@PathVariable Long id, @RequestBody RawMaterialDto rawMaterialDto) {
        return rawMaterialService.updateRawMaterial(id, rawMaterialDto);
    }

    @GetMapping(path = "/{id}")
    public RawMaterialDto getRawMaterialById(@PathVariable Long id) {
        return rawMaterialService.getRawMaterialById(id);
    }

    @GetMapping("low-stock")
    public List<RawMaterialDto> getLowStockMaterials(){
        return rawMaterialService.getLowStockMaterials();
    }
}
