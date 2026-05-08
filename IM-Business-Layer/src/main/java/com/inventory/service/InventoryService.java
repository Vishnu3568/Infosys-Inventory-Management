package com.inventory.service;

import com.inventory.dao.ProductDAO;
import com.inventory.database_system.entity.Product;
import com.inventory.Report.InventoryReportService;
import com.inventory.service.validation.InventoryValidator;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final ProductDAO productDAO;
    private final InventoryValidator validator;
    private final InventoryReportService reportService;

    public InventoryService(ProductDAO productDAO,
                            InventoryValidator validator,
                            InventoryReportService reportService) {
        this.productDAO = productDAO;
        this.validator = validator;
        this.reportService = reportService;
    }

    // id type changed: int → Long
    // field renamed: stock → quantity
    public void reduceStock(Long id, int quantity) {

        System.out.println("[Inventory] Reducing stock | ID: " + id + ", Qty: " + quantity);

        validator.validateQuantity(quantity);

        Product product = productDAO.getProductById(id);

        validator.validateProductExists(product, id);
        validator.validateStock(product.getQuantity(), quantity);   // was: getStock()

        int newQuantity = product.getQuantity() - quantity;         // was: getStock()

        productDAO.updateStock(id, newQuantity);

        System.out.println("[Inventory] Stock reduced successfully | New Quantity: " + newQuantity);

        reportService.checkAndSendLowStockAlert("SYSTEM", "AUTOMATED");
    }

    // id type changed: int → Long
    // field renamed: stock → quantity
    public void increaseStock(Long id, int quantity) {

        System.out.println("[Inventory] Increasing stock | ID: " + id + ", Qty: " + quantity);

        validator.validateQuantity(quantity);

        Product product = productDAO.getProductById(id);

        validator.validateProductExists(product, id);

        int newQuantity = product.getQuantity() + quantity;         // was: getStock()

        productDAO.updateStock(id, newQuantity);

        System.out.println("[Inventory] Stock increased successfully | New Quantity: " + newQuantity);
    }

    public int getStock(Long id) {

        Product product = productDAO.getProductById(id);

        validator.validateProductExists(product, id);

        return product.getQuantity();                                // was: getStock()
    }
}
