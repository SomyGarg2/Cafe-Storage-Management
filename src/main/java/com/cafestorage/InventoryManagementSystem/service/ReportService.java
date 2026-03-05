package com.cafestorage.InventoryManagementSystem.service;

import com.cafestorage.InventoryManagementSystem.dto.DailyUsageReportDto;
import com.cafestorage.InventoryManagementSystem.dto.MonthlyPurchaseSummaryDto;
import com.cafestorage.InventoryManagementSystem.repository.StockInwardRepository;
import com.cafestorage.InventoryManagementSystem.repository.StockOutwardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    private final StockOutwardRepository stockOutwardRepository;
    private final StockInwardRepository stockInwardRepository;



    public List<DailyUsageReportDto> getDailyUsageReport(LocalDate date) {

        if (date == null) {
            date = LocalDate.now(); // default to today
        }

        return stockOutwardRepository.findDailyUsageReport(date);
    }

    public List<MonthlyPurchaseSummaryDto> getMonthlyPurchaseSummary(Integer year, Integer month) {

        if (year == null) {
            year = LocalDate.now().getYear();
        }
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }

        // Validate month range
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        return stockInwardRepository.findMonthlyPurchaseSummary(year, month);
    }
}
