package com.cafestorage.InventoryManagementSystem.service;

import com.cafestorage.InventoryManagementSystem.dto.SupplierDto;
import com.cafestorage.InventoryManagementSystem.entity.Supplier;
import com.cafestorage.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.cafestorage.InventoryManagementSystem.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierService(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    public SupplierDto addSupplier(SupplierDto supplierDto){
        Supplier supplier = modelMapper.map(supplierDto, Supplier.class);
        supplier.setStatus(supplierDto.getStatus());
        Supplier savedSupplier = supplierRepository.save(supplier);
        return modelMapper.map(savedSupplier, SupplierDto.class);
    }

    public SupplierDto getSupplierById(Long id){
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
        return modelMapper.map(supplier, SupplierDto.class);
    }

    public List<SupplierDto> getAllSuppliers(){
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplier -> modelMapper.map(supplier, SupplierDto.class))
                .toList();
    }

    public SupplierDto updateSupplier(Long id, SupplierDto supplierDto){
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        existingSupplier.setName(supplierDto.getName());
        existingSupplier.setContact(supplierDto.getContact());
        existingSupplier.setAddress(supplierDto.getAddress());
        existingSupplier.setStatus(supplierDto.getStatus());

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return modelMapper.map(updatedSupplier, SupplierDto.class);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplier.setStatus(false);

        supplierRepository.save(supplier);
    }


}
