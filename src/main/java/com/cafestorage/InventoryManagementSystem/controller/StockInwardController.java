package com.cafestorage.InventoryManagementSystem.controller;

import com.cafestorage.InventoryManagementSystem.dto.StockInwardDto;
import com.cafestorage.InventoryManagementSystem.service.StockInwardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock-inwards")
public class StockInwardController {

    private final StockInwardService stockInwardService;
    public StockInwardController(StockInwardService stockInwardService) {
        this.stockInwardService = stockInwardService;
    }


    @PostMapping
    public StockInwardDto recordStockInward(@RequestBody StockInwardDto stockInwardDto) {
        return stockInwardService.recordStockInward(stockInwardDto);
    }





}
