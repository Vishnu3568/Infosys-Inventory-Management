package com.inventory.database_system.service;

import com.inventory.database_system.enums.TransactionStatus;
import com.inventory.database_system.enums.TransactionType;
import com.inventory.database_system.repository.CategoryRepository;
import com.inventory.database_system.repository.ProductRepository;
import com.inventory.database_system.repository.SupplierRepository;
import com.inventory.database_system.repository.TransactionRepository;
import com.inventory.database_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    public ReportService(TransactionRepository transactionRepository,
                         ProductRepository productRepository,
                         CategoryRepository categoryRepository,
                         SupplierRepository supplierRepository,
                         UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
    }

    // Dashboard summary using simple counts
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalProducts", productRepository.count());
        dashboard.put("totalCategories", categoryRepository.count());
        dashboard.put("totalSuppliers", supplierRepository.count());
        dashboard.put("totalUsers", userRepository.count());
        dashboard.put("totalTransactions", transactionRepository.count());
        dashboard.put("salesTransactions", transactionRepository.findByTransactionType(TransactionType.SALE).size());
        dashboard.put("purchaseTransactions", transactionRepository.findByTransactionType(TransactionType.PURCHASE).size());
        dashboard.put("pendingTransactions", transactionRepository.findByTransactionStatus(TransactionStatus.PENDING).size());

        return dashboard;
    }
}
