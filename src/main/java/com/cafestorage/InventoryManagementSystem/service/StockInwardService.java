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
    public StockInwardDto recordStockInward(StockInwardDto stockInwardDto){
        RawMaterial rawMaterial = rawMaterialRepository.findById(stockInwardDto.getRawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        Supplier supplier = supplierRepository.findById(stockInwardDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        StockInward stockInward = new StockInward();
        stockInward.setRawMaterial(rawMaterial);
        stockInward.setSupplier(supplier);
        stockInward.setQuantity(stockInwardDto.getQuantity());
        stockInward.setUnitPrice(stockInwardDto.getUnitPrice());

        stockInward.setTotalPrice(stockInward.getQuantity()*stockInward.getUnitPrice());

        stockInwardRepository.save(stockInward);

        // Automatically increase inventory
        rawMaterial.setQuantity(
                rawMaterial.getQuantity() + stockInwardDto.getQuantity()
        );
        rawMaterialRepository.save(rawMaterial);

        return modelMapper.map(stockInward, StockInwardDto.class);
    }


}
