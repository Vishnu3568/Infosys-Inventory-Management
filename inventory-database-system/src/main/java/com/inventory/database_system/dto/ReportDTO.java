package com.inventory.database_system.dto;

import lombok.*;
import java.math.BigDecimal;

public class ReportDTO {

    // Category revenue breakdown
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryRevenue {
        private String categoryName;
        private BigDecimal revenue;
    }

    // Monthly sales summary
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MonthlySales {
        private int year;
        private int month;
        private BigDecimal totalRevenue;
        private long transactionCount;
    }

    // Inventory report data
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InventoryReport {
        private BigDecimal totalInventoryValue;
        private long totalProducts;
        private long lowStockCount;
    }

    // Product stock info
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductStock {
        private String productName;
        private String sku;
        private int currentStock;
        private int reorderLevel;
    }

    // Top product by revenue
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TopProduct {
        private String productName;
        private BigDecimal totalRevenue;
        private long totalQuantitySold;
    }
}
