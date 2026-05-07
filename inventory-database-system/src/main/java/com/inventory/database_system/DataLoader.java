package com.inventory.database_system;

import com.inventory.database_system.entity.*;
import com.inventory.database_system.enums.*;
import com.inventory.database_system.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CategoryRepository categoryRepo,
                               SupplierRepository supplierRepo,
                               UserRepository userRepo,
                               ProductRepository productRepo,
                               TransactionRepository transactionRepo) {

        return args -> {

            // Only insert data if the database is empty
            if (categoryRepo.count() > 0) {
                System.out.println("ℹ️  Data already exists, skipping seed.");
                return;
            }

            System.out.println("🔄 Seeding database...");

            // ═══════════════════════════════════════
            // CATEGORIES
            // ═══════════════════════════════════════
            Category electronics = categoryRepo.save(Category.builder()
                    .name("Electronics")
                    .description("Electronic devices and gadgets")
                    .build());
            Category clothing = categoryRepo.save(Category.builder()
                    .name("Clothing")
                    .description("Apparel and fashion wear")
                    .build());
            Category food = categoryRepo.save(Category.builder()
                    .name("Food & Beverages")
                    .description("Food items and drinks")
                    .build());
            Category furniture = categoryRepo.save(Category.builder()
                    .name("Furniture")
                    .description("Office and home furniture")
                    .build());
            Category stationery = categoryRepo.save(Category.builder()
                    .name("Stationery")
                    .description("Writing and office supplies")
                    .build());

            // ═══════════════════════════════════════
            // SUPPLIERS
            // ═══════════════════════════════════════
            Supplier dell = supplierRepo.save(Supplier.builder()
                    .name("Dell Technologies")
                    .contactInfo("9876543210")
                    .address("Hyderabad, India")
                    .build());
            Supplier samsung = supplierRepo.save(Supplier.builder()
                    .name("Samsung India")
                    .contactInfo("9876543211")
                    .address("Noida, India")
                    .build());
            Supplier nike = supplierRepo.save(Supplier.builder()
                    .name("Nike India")
                    .contactInfo("9876543212")
                    .address("Mumbai, India")
                    .build());
            Supplier ikea = supplierRepo.save(Supplier.builder()
                    .name("IKEA")
                    .contactInfo("9876543213")
                    .address("Bengaluru, India")
                    .build());
            Supplier nestle = supplierRepo.save(Supplier.builder()
                    .name("Nestle India")
                    .contactInfo("9876543214")
                    .address("Gurgaon, India")
                    .build());

            // ═══════════════════════════════════════
            // USERS
            // ═══════════════════════════════════════
            User admin = userRepo.save(User.builder()
                    .name("Admin User")
                    .email("admin@inventory.com")
                    .password("admin123")
                    .role(UserRole.ADMIN)
                    .phoneNumber("9000000001")
                    .build());
            User manager = userRepo.save(User.builder()
                    .name("Ravi Kumar")
                    .email("ravi@inventory.com")
                    .password("manager123")
                    .role(UserRole.MANAGER)
                    .phoneNumber("9000000002")
                    .build());
            User staff1 = userRepo.save(User.builder()
                    .name("Priya Sharma")
                    .email("priya@inventory.com")
                    .password("staff123")
                    .role(UserRole.STAFF)
                    .phoneNumber("9000000003")
                    .build());
            User staff2 = userRepo.save(User.builder()
                    .name("Amit Patel")
                    .email("amit@inventory.com")
                    .password("staff456")
                    .role(UserRole.STAFF)
                    .phoneNumber("9000000004")
                    .build());

            // ═══════════════════════════════════════
            // PRODUCTS
            // ═══════════════════════════════════════
            Product laptop = productRepo.save(Product.builder()
                    .name("Dell Laptop Inspiron 15").sku("ELEC-001")
                    .price(new BigDecimal("75000.00")).quantity(50).reorderLevel(10)
                    .category(electronics).supplier(dell)
                    .description("15-inch laptop with i7 processor")
                    .build());
            Product phone = productRepo.save(Product.builder()
                    .name("Samsung Galaxy S24").sku("ELEC-002")
                    .price(new BigDecimal("85000.00")).quantity(80).reorderLevel(15)
                    .category(electronics).supplier(samsung)
                    .description("Flagship smartphone")
                    .build());
            Product monitor = productRepo.save(Product.builder()
                    .name("Dell 27\" Monitor").sku("ELEC-003")
                    .price(new BigDecimal("25000.00")).quantity(30).reorderLevel(5)
                    .category(electronics).supplier(dell)
                    .description("4K UHD monitor")
                    .build());
            Product headphones = productRepo.save(Product.builder()
                    .name("Samsung Earbuds Pro").sku("ELEC-004")
                    .price(new BigDecimal("12000.00")).quantity(100).reorderLevel(20)
                    .category(electronics).supplier(samsung)
                    .description("Wireless earbuds with ANC")
                    .build());
            Product tshirt = productRepo.save(Product.builder()
                    .name("Nike Dri-FIT T-Shirt").sku("CLTH-001")
                    .price(new BigDecimal("2500.00")).quantity(200).reorderLevel(30)
                    .category(clothing).supplier(nike)
                    .description("Sports performance t-shirt")
                    .build());
            Product shoes = productRepo.save(Product.builder()
                    .name("Nike Air Max 90").sku("CLTH-002")
                    .price(new BigDecimal("12000.00")).quantity(60).reorderLevel(10)
                    .category(clothing).supplier(nike)
                    .description("Classic running shoes")
                    .build());
            Product coffee = productRepo.save(Product.builder()
                    .name("Nescafe Gold Blend").sku("FOOD-001")
                    .price(new BigDecimal("450.00")).quantity(300).reorderLevel(50)
                    .category(food).supplier(nestle)
                    .description("Premium instant coffee 200g")
                    .build());
            Product chocolate = productRepo.save(Product.builder()
                    .name("KitKat Box").sku("FOOD-002")
                    .price(new BigDecimal("250.00")).quantity(500).reorderLevel(75)
                    .category(food).supplier(nestle)
                    .description("Box of 24 KitKat bars")
                    .build());
            Product desk = productRepo.save(Product.builder()
                    .name("IKEA Standing Desk").sku("FURN-001")
                    .price(new BigDecimal("18000.00")).quantity(20).reorderLevel(5)
                    .category(furniture).supplier(ikea)
                    .description("Adjustable standing desk")
                    .build());
            Product chair = productRepo.save(Product.builder()
                    .name("IKEA Office Chair").sku("FURN-002")
                    .price(new BigDecimal("12000.00")).quantity(35).reorderLevel(8)
                    .category(furniture).supplier(ikea)
                    .description("Ergonomic office chair")
                    .build());
            Product notebook = productRepo.save(Product.builder()
                    .name("Classmate Notebook A4").sku("STAT-001")
                    .price(new BigDecimal("80.00")).quantity(1000).reorderLevel(100)
                    .category(stationery).supplier(ikea)
                    .description("200-page ruled notebook")
                    .build());
            Product pen = productRepo.save(Product.builder()
                    .name("Parker Ballpoint Pen").sku("STAT-002")
                    .price(new BigDecimal("350.00")).quantity(500).reorderLevel(50)
                    .category(stationery).supplier(ikea)
                    .description("Blue ink ballpoint pen")
                    .build());

            // ═══════════════════════════════════════
            // TRANSACTIONS
            // ═══════════════════════════════════════
            LocalDateTime now = LocalDateTime.now();

            // Sales
            transactionRepo.save(Transaction.builder()
                    .product(laptop).user(admin).quantity(5)
                    .totalPrice(new BigDecimal("375000.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Bulk laptop sale")
                    .notes("Corporate order")
                    .referenceId("SALE-2026-001")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(phone).user(staff1).quantity(10)
                    .totalPrice(new BigDecimal("850000.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Phone sale to retailer")
                    .notes("Retail distributor order")
                    .referenceId("SALE-2026-002")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(tshirt).user(staff2).quantity(50)
                    .totalPrice(new BigDecimal("125000.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("T-shirt bulk order")
                    .notes("Sports event order")
                    .referenceId("SALE-2026-003")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(shoes).user(manager).quantity(8)
                    .totalPrice(new BigDecimal("96000.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Shoe retail sale")
                    .referenceId("SALE-2026-004")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(coffee).user(staff1).quantity(100)
                    .totalPrice(new BigDecimal("45000.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Coffee wholesale")
                    .notes("Hotel supply")
                    .referenceId("SALE-2026-005")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(monitor).user(admin).quantity(3)
                    .totalPrice(new BigDecimal("75000.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Monitor sale")
                    .referenceId("SALE-2026-006")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(headphones).user(staff2).quantity(20)
                    .totalPrice(new BigDecimal("240000.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Earbuds sale")
                    .notes("Online store order")
                    .referenceId("SALE-2026-007")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(desk).user(manager).quantity(2)
                    .totalPrice(new BigDecimal("36000.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Desk sale")
                    .referenceId("SALE-2026-008")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(chocolate).user(staff1).quantity(50)
                    .totalPrice(new BigDecimal("12500.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.PENDING)
                    .description("Pending chocolate order")
                    .notes("Awaiting delivery confirmation")
                    .referenceId("SALE-2026-009")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(pen).user(staff2).quantity(30)
                    .totalPrice(new BigDecimal("10500.00"))
                    .transactionType(TransactionType.SALE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Pen sale")
                    .referenceId("SALE-2026-010")
                    .build());

            // Purchases (with supplier)
            transactionRepo.save(Transaction.builder()
                    .product(laptop).supplier(dell).user(admin).quantity(20)
                    .totalPrice(new BigDecimal("1200000.00"))
                    .transactionType(TransactionType.PURCHASE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Laptop restock from Dell")
                    .referenceId("PO-2026-001")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(phone).supplier(samsung).user(manager).quantity(50)
                    .totalPrice(new BigDecimal("3400000.00"))
                    .transactionType(TransactionType.PURCHASE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Phone restock from Samsung")
                    .referenceId("PO-2026-002")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(tshirt).supplier(nike).user(staff1).quantity(100)
                    .totalPrice(new BigDecimal("200000.00"))
                    .transactionType(TransactionType.PURCHASE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("T-shirt restock from Nike")
                    .referenceId("PO-2026-003")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(coffee).supplier(nestle).user(manager).quantity(200)
                    .totalPrice(new BigDecimal("72000.00"))
                    .transactionType(TransactionType.PURCHASE)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Coffee restock from Nestle")
                    .referenceId("PO-2026-004")
                    .build());
            transactionRepo.save(Transaction.builder()
                    .product(chair).supplier(ikea).user(admin).quantity(15)
                    .totalPrice(new BigDecimal("144000.00"))
                    .transactionType(TransactionType.PURCHASE)
                    .transactionStatus(TransactionStatus.PENDING)
                    .description("Pending chair order from IKEA")
                    .notes("Awaiting shipment")
                    .referenceId("PO-2026-005")
                    .build());

            // Returns
            transactionRepo.save(Transaction.builder()
                    .product(phone).user(staff2).quantity(2)
                    .totalPrice(new BigDecimal("170000.00"))
                    .transactionType(TransactionType.RETURN)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Defective phones returned")
                    .referenceId("RMA-2026-001")
                    .build());

            // Adjustments
            transactionRepo.save(Transaction.builder()
                    .product(notebook).user(admin).quantity(50)
                    .totalPrice(new BigDecimal("4000.00"))
                    .transactionType(TransactionType.ADJUSTMENT)
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .description("Stock adjustment after audit")
                    .notes("Physical count correction")
                    .referenceId("ADJ-2026-001")
                    .build());

            System.out.println("✅ Database seeded successfully with sample data!");
            System.out.println("   📁 " + categoryRepo.count() + " categories");
            System.out.println("   🏭 " + supplierRepo.count() + " suppliers");
            System.out.println("   👤 " + userRepo.count() + " users");
            System.out.println("   📦 " + productRepo.count() + " products");
            System.out.println("   💰 " + transactionRepo.count() + " transactions");
        };
    }
}