package com.inventory.controller;

import com.inventory.database_system.entity.Category;
import com.inventory.database_system.entity.Product;
import com.inventory.database_system.entity.Supplier;
import com.inventory.database_system.repository.CategoryRepository;
import com.inventory.database_system.repository.SupplierRepository;
import com.inventory.dto.ProductRequestDTO;
import com.inventory.service.ProductService;
import com.inventory.service.InventoryService;
import com.inventory.Report.InventoryReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
@CrossOrigin(origins = "${APP_CORS_ORIGIN:http://localhost:3000}")
@RequestMapping("/api")
public class Controller {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final InventoryReportService reportService;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public Controller(ProductService productService,
                      InventoryService inventoryService,
                      InventoryReportService reportService,
                      CategoryRepository categoryRepository,
                      SupplierRepository supplierRepository) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.reportService = reportService;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    // ──────────────────────── PRODUCT ────────────────────────────────────────

    /**
     * POST /api/product
     *
     * Body example:
     * {
     *   "name":        "Wireless Mouse",
     *   "sku":         "WM-001",
     *   "description": "Ergonomic wireless mouse",
     *   "price":       29.99,
     *   "quantity":    100,
     *   "reorderLevel": 15,
     *   "category":    { "id": 1 },
     *   "supplier":    { "id": 2 }
     * }
     */
    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequestDTO request) {
        Product product = toProduct(request);
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody ProductRequestDTO request) {
        Product existing = productService.getProduct(id);
        applyUpdate(existing, request);
        Product updated = productService.updateProduct(existing);
        return ResponseEntity.ok(updated);
    }

    /**
     * GET /api/products
     * Returns ALL products including inactive ones.
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * GET /api/products/active
     * Returns only active products (active = true).
     */
    @GetMapping("/products/active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        return ResponseEntity.ok(productService.getActiveProducts());
    }

    /**
     * GET /api/products/low-stock
     */
    @GetMapping("/products/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        return ResponseEntity.ok(productService.getLowStockProducts());
    }

    /**
     * GET /api/product/{id}
     * Changed path variable type from int → Long to match new entity id type.
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    /**
     * GET /api/products/search?name=mouse
     * Search products by name (case-insensitive, partial match).
     */
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    /**
     * GET /api/products/category/{categoryId}
     */
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    /**
     * GET /api/products/supplier/{supplierId}
     */
    @GetMapping("/products/supplier/{supplierId}")
    public ResponseEntity<List<Product>> getProductsBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(productService.getProductsBySupplier(supplierId));
    }

    /**
     * DELETE /api/product/{id}
     * Hard delete. Use /api/product/{id}/deactivate for soft delete.
     * Changed path variable type from int → Long.
     */
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Product deleted"));
    }

    /**
     * PATCH /api/product/{id}/deactivate
     * Soft delete — sets active = false, preserves transaction history.
     */
    @PatchMapping("/product/{id}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.ok("Product deactivated");
    }

    // ──────────────────────── INVENTORY ──────────────────────────────────────

    /**
     * POST /api/reduce/{id}/{qty}
     * Changed path variable type from int → Long for id.
     */
    @PostMapping("/reduce/{id}/{qty}")
    public ResponseEntity<String> reduceStock(@PathVariable Long id,
                                              @PathVariable int qty) {
        inventoryService.reduceStock(id, qty);
        return ResponseEntity.ok("Stock reduced");
    }

    /**
     * POST /api/increase/{id}/{qty}
     * Changed path variable type from int → Long for id.
     */
    @PostMapping("/increase/{id}/{qty}")
    public ResponseEntity<String> increaseStock(@PathVariable Long id,
                                                @PathVariable int qty) {
        inventoryService.increaseStock(id, qty);
        return ResponseEntity.ok("Stock increased");
    }

    // ──────────────────────── REPORT ─────────────────────────────────────────

    /**
     * GET /api/report
     * Triggers low-stock check and sends email alert if needed.
     * Authorized Roles: Admin, Supplier, Staff
     */
    @GetMapping("/report")
    public ResponseEntity<String> generateReport(@RequestParam String username, @RequestParam String role) {
        System.out.println("Controller: Generating report triggered by " + username + " (" + role + ")");
        
        // Role check
        String upperRole = role.toUpperCase();
        if (!upperRole.equals("ADMIN") && !upperRole.equals("SUPPLIER") && !upperRole.equals("STAFF")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You do not have permission to generate alerts.");
        }

        try {
            reportService.checkAndSendLowStockAlert(username, role);
            return ResponseEntity.ok("Low stock alert sent successfully.");
        } catch (Exception e) {
            System.err.println("Controller ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate report: " + e.getMessage());
        }
    }

    private Product toProduct(ProductRequestDTO request) {
        Product product = new Product();
        applyUpdate(product, request);
        return product;
    }

    private void applyUpdate(Product product, ProductRequestDTO request) {
        if (request.getName() != null) {
            product.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription().trim());
        }
        if (request.getSku() != null && !request.getSku().isBlank()) {
            product.setSku(request.getSku().trim());
        } else if (product.getSku() == null) {
            // Auto-generate SKU if missing (e.g. LAP-1234)
            String prefix = (product.getName() != null && product.getName().length() >= 3) 
                ? product.getName().substring(0, 3).toUpperCase() 
                : "PRD";
            product.setSku(prefix + "-" + (int)(Math.random() * 9000 + 1000));
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getQuantity() != null) {
            product.setQuantity(request.getQuantity());
        }
        if (request.getReorderLevel() != null) {
            product.setReorderLevel(request.getReorderLevel());
        }
        if (request.getCategory() != null && !request.getCategory().isBlank()) {
            String categoryName = request.getCategory().trim();
            product.setCategory(resolveCategory(categoryName, product.getCategory()));
        }
        if (request.getSupplier() != null && !request.getSupplier().isBlank()) {
            String supplierName = request.getSupplier().trim();
            product.setSupplier(resolveSupplier(supplierName, product.getSupplier()));
        }
        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }
    }

    private Category resolveCategory(String incomingName, Category current) {
        if (current != null && sameName(current.getName(), incomingName)) {
            return current;
        }
        return categoryRepository.findByNameIgnoreCase(incomingName)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setName(incomingName);
                    return categoryRepository.save(category);
                });
    }

    private Supplier resolveSupplier(String incomingName, Supplier current) {
        if (current != null && sameName(current.getName(), incomingName)) {
            return current;
        }
        return supplierRepository.findFirstByNameIgnoreCase(incomingName)
                .orElseGet(() -> {
                    Supplier supplier = new Supplier();
                    supplier.setName(incomingName);
                    return supplierRepository.save(supplier);
                });
    }

    private boolean sameName(String existing, String incoming) {
        return existing != null && existing.equalsIgnoreCase(incoming);
    }
}

