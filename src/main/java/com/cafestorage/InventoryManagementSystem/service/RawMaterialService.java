package com.cafestorage.InventoryManagementSystem.service;

import com.cafestorage.InventoryManagementSystem.dto.RawMaterialDto;
import com.cafestorage.InventoryManagementSystem.entity.RawMaterial;
import com.cafestorage.InventoryManagementSystem.entity.Supplier;
import com.cafestorage.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.cafestorage.InventoryManagementSystem.repository.RawMaterialRepository;
import com.cafestorage.InventoryManagementSystem.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {
    private final RawMaterialRepository rawMaterialRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository,
                              SupplierRepository supplierRepository,
                              ModelMapper modelMapper) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    public RawMaterialDto addRawMaterial(RawMaterialDto rawMaterialDto){
        if (rawMaterialRepository.existsByName(rawMaterialDto.getName())) {
            throw new IllegalStateException("Raw material already exists");
        }

        Supplier supplier = supplierRepository.findById(rawMaterialDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        if (!supplier.isStatus()) {
            throw new IllegalStateException("Supplier is inactive. Cannot add raw material.");
        }

        RawMaterial rawMaterial = modelMapper.map(rawMaterialDto, RawMaterial.class);

        rawMaterial.setSupplier(supplier);
        RawMaterial savedRawMaterial = rawMaterialRepository.save(rawMaterial);
        return modelMapper.map(savedRawMaterial, RawMaterialDto.class);
    }

    public RawMaterialDto updateRawMaterial(Long id, RawMaterialDto rawMaterialDto) {

        RawMaterial existingRawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        Supplier supplier = supplierRepository.findById(rawMaterialDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        existingRawMaterial.setName(rawMaterialDto.getName());
        existingRawMaterial.setUnit(rawMaterialDto.getUnit());
        existingRawMaterial.setCategory(rawMaterialDto.getCategory());
        existingRawMaterial.setMinStockLevel(rawMaterialDto.getMinStockLevel());
        existingRawMaterial.setSupplier(supplier);

        RawMaterial updatedRawMaterial = rawMaterialRepository.save(existingRawMaterial);
        return modelMapper.map(updatedRawMaterial, RawMaterialDto.class);
    }

    public RawMaterialDto getRawMaterialById(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));
        return modelMapper.map(rawMaterial, RawMaterialDto.class);
    }

    public List<RawMaterialDto> getLowStockMaterials() {
        List<RawMaterial> lowStockMaterials = rawMaterialRepository.findLowStockMaterials();

        return lowStockMaterials.stream()
                .map(material -> modelMapper.map(material, RawMaterialDto.class))
                .toList();
    }



}
