package com.inventory.database_system.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {

    private long totalProducts;
    private long totalCategories;
    private long totalSuppliers;
    private long totalUsers;
    private long totalTransactions;
    private long salesTransactions;
    private long purchaseTransactions;
    private long pendingTransactions;
}
