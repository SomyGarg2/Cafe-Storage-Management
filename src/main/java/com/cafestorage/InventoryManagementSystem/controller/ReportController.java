package com.cafestorage.InventoryManagementSystem.controller;

import com.cafestorage.InventoryManagementSystem.dto.DailyUsageReportDto;
import com.cafestorage.InventoryManagementSystem.dto.MonthlyPurchaseSummaryDto;
import com.cafestorage.InventoryManagementSystem.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/daily-usage")
    public List<DailyUsageReportDto> getDailyUsageReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return reportService.getDailyUsageReport(date);
    }

    @GetMapping("/monthly-purchase")
    public List<MonthlyPurchaseSummaryDto> getMonthlyPurchaseSummary(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        return reportService.getMonthlyPurchaseSummary(year, month);
    }
}
