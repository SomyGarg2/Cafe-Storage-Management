package com.cafestorage.InventoryManagementSystem.service;

import com.cafestorage.InventoryManagementSystem.dto.StockInwardDto;
import com.cafestorage.InventoryManagementSystem.entity.RawMaterial;
import com.cafestorage.InventoryManagementSystem.entity.StockInward;
import com.cafestorage.InventoryManagementSystem.entity.Supplier;
import com.cafestorage.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.cafestorage.InventoryManagementSystem.repository.RawMaterialRepository;
import com.cafestorage.InventoryManagementSystem.repository.StockInwardRepository;
import com.cafestorage.InventoryManagementSystem.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StockInwardService {

    private final StockInwardRepository stockInwardRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public StockInwardService(StockInwardRepository stockInwardRepository,
                              RawMaterialRepository rawMaterialRepository,
                              SupplierRepository supplierRepository,
                              ModelMapper modelMapper) {
        this.stockInwardRepository = stockInwardRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public StockInwardDto recordStockInward(StockInwardDto dto) {

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        if (dto.getUnitPrice() == null) {
            throw new IllegalArgumentException("Unit price is required");
        }

        StockInward stockInward = new StockInward();
        stockInward.setRawMaterial(rawMaterial);
        stockInward.setSupplier(supplier);
        stockInward.setQuantity(dto.getQuantity());
        stockInward.setUnitPrice(dto.getUnitPrice());
        stockInward.setPurchaseDate(LocalDateTime.now());

        double totalPrice = dto.getQuantity() * dto.getUnitPrice();
        stockInward.setTotalPrice(totalPrice);

        // Increase inventory
        rawMaterial.setQuantity(rawMaterial.getQuantity() + dto.getQuantity());
        rawMaterialRepository.save(rawMaterial);

        StockInward savedStockInward = stockInwardRepository.save(stockInward);

        return modelMapper.map(savedStockInward, StockInwardDto.class);
    }
}




