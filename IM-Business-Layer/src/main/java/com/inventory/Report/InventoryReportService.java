package com.inventory.Report;
import com.inventory.dao.ProductDAO;
import com.inventory.database_system.entity.Product;
import com.inventory.database_system.entity.AlertHistory;
import com.inventory.database_system.repository.AlertHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Service
public class InventoryReportService {

    private final ProductDAO productDAO;
    private final EmailService emailService;
    private final AlertHistoryRepository alertHistoryRepository;
 
    public InventoryReportService(ProductDAO productDAO, EmailService emailService, AlertHistoryRepository alertHistoryRepository) {
        this.productDAO = productDAO;
        this.emailService = emailService;
        this.alertHistoryRepository = alertHistoryRepository;
    }
 
    public void checkAndSendLowStockAlert(String username, String role) {
        try {
            List<Product> allProducts = productDAO.getAllProducts();
            List<Product> lowStockProducts = new ArrayList<>();
 
            for (Product product : allProducts) {
                if (product.isLowStock()) {
                    lowStockProducts.add(product);
                }
            }
 
            if (lowStockProducts.isEmpty()) {
                System.out.println("Inventory Check: All products sufficiently stocked.");
                return;
            }
 
            // 1. Send professional alert to specified recipient
            sendProfessionalLowStockAlert(lowStockProducts);
 
            // 2. Log and store history
            AlertHistory history = new AlertHistory(username, role, LocalDateTime.now(), lowStockProducts.size());
            alertHistoryRepository.save(history);
            System.out.println("Alert history stored for user: " + username);

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR during report generation:");
            e.printStackTrace(); // This will show the exact line number of the crash in the terminal
            throw new RuntimeException("Report Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
 
    private void sendProfessionalLowStockAlert(List<Product> products) {
        String recipient = "u.vishnu3568@gmail.com";
        String subject = "Low Stock Inventory Alert";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = LocalDateTime.now().format(formatter);

        StringBuilder body = new StringBuilder();
        body.append("Dear Administrator / Stakeholder,\n\n");
        body.append("This is an automated professional alert from the InvenTrack System.\n\n");
        body.append("--- ALERT SUMMARY ---\n");
        body.append("Date and Time Generated: ").append(formattedDate).append("\n");
        body.append("Total Low Stock Items Count: ").append(products.size()).append("\n\n");
        
        body.append(String.format("%-25s | %-15s | %-10s | %-10s\n", "Product Name", "SKU", "Qty", "Min Req"));
        body.append("--------------------------------------------------------------------------\n");
        
        for (Product p : products) {
            body.append(String.format("%-25s | %-15s | %-10d | %-10d\n",
                        truncate(p.getName(), 24), 
                        p.getSku(), 
                        p.getQuantity(), 
                        p.getReorderLevel()));
        }
        
        body.append("\nURGENT: The above items have fallen below their minimum required stock levels. Please arrange for an immediate restock to avoid operational delays.\n\n");
        body.append("Regards,\n");
        body.append("InvenTrack System\n");

        emailService.sendEmail(recipient, subject, body.toString());
        System.out.println("Professional low stock alert sent to: " + recipient);
    }

    private String truncate(String text, int length) {
        if (text == null) return "N/A";
        return text.length() > length ? text.substring(0, length - 3) + "..." : text;
    }
}
