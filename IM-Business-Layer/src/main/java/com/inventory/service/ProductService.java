package com.inventory.service;

import com.inventory.dao.ProductDAO;
import com.inventory.database_system.entity.Product;
import com.inventory.service.validation.ProductValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductDAO productDAO;
    private final ProductValidator validator;

    public ProductService(ProductDAO productDAO, ProductValidator validator) {
        this.productDAO = productDAO;
        this.validator = validator;
    }

    public void addProduct(Product product) {
        normalizeProduct(product);
        validator.validate(product);
        productDAO.saveProduct(product);
    }

    public Product updateProduct(Product product) {
        normalizeProduct(product);
        validator.validate(product);
        return productDAO.updateProduct(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public List<Product> getActiveProducts() {
        return productDAO.getActiveProducts();
    }

    // id type changed: int → Long
    public Product getProduct(Long id) {
        return productDAO.getProductById(id);
    }

    public List<Product> searchByName(String name) {
        return productDAO.searchByName(name);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productDAO.getProductsByCategory(categoryId);
    }

    public List<Product> getProductsBySupplier(Long supplierId) {
        return productDAO.getProductsBySupplier(supplierId);
    }

    public List<Product> getLowStockProducts() {
        return productDAO.getAllProducts().stream()
                .filter(Product::isLowStock)
                .toList();
    }

    // id type changed: int → Long
    public void deleteProduct(Long id) {
        productDAO.deleteProduct(id);
    }

    public void deactivateProduct(Long id) {
        productDAO.deactivateProduct(id);
    }

    private void normalizeProduct(Product product) {
        if (product.getSku() == null || product.getSku().isBlank()) {
            product.setSku(generateSku(product.getName()));
        }

        if (product.getPrice() == null) {
            product.setPrice(BigDecimal.ZERO);
        }

        if (product.getReorderLevel() == null) {
            product.setReorderLevel(10);
        }
    }

    private String generateSku(String name) {
        String base = name == null ? "ITEM" : name.replaceAll("[^A-Za-z0-9]+", "-")
                .replaceAll("^-+|-+$", "")
                .toUpperCase();
        if (base.isBlank()) {
            base = "ITEM";
        }
        return base + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
