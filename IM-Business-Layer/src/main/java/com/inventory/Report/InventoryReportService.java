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
                System.out.println("Check finished: Stocks are all good.");
                return;
            }
 
            sendLowStockAlert(lowStockProducts);
 
            // Log this alert in history
            AlertHistory history = new AlertHistory(username, role, LocalDateTime.now(), lowStockProducts.size());
            alertHistoryRepository.save(history);
            System.out.println("Alert logged for: " + username);

        } catch (Exception e) {
            System.err.println("Error generating report:");
            e.printStackTrace();
            throw new RuntimeException("Report Generation Failed: " + e.getMessage());
        }
    }
 
    private void sendLowStockAlert(List<Product> products) {
        String recipient = "u.vishnu3568@gmail.com";
        String subject = "Inventory Alert: Low Stock Items";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = LocalDateTime.now().format(formatter);

        StringBuilder body = new StringBuilder();
        body.append("Hi Team,\n\n");
        body.append("The following items are running low and need to be restocked:\n\n");
        body.append("Date: ").append(formattedDate).append("\n\n");
        
        body.append(String.format("%-25s | %-15s | %-10s\n", "Product", "SKU", "Qty Left"));
        body.append("----------------------------------------------------------\n");
        
        for (Product p : products) {
            body.append(String.format("%-25s | %-15s | %-10d\n",
                        truncate(p.getName(), 24), 
                        p.getSku(), 
                        p.getQuantity()));
        }
        
        body.append("\nPlease check the system for details.\n\n");
        body.append("InvenTrack System\n");

        emailService.sendEmail(recipient, subject, body.toString());
    }

    private String truncate(String text, int length) {
        if (text == null) return "N/A";
        return text.length() > length ? text.substring(0, length - 3) + "..." : text;
    }
}
