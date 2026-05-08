package com.inventory.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RoleProtectedController {

    @GetMapping("/admin/dashboard")
    public ResponseEntity<Map<String, String>> adminDashboard() {
        return ResponseEntity.ok(Map.of("message", "Admin access granted"));
    }

    @GetMapping("/supplier/dashboard")
    public ResponseEntity<Map<String, String>> supplierDashboard() {
        return ResponseEntity.ok(Map.of("message", "Supplier access granted"));
    }

    @GetMapping("/staff/dashboard")
    public ResponseEntity<Map<String, String>> staffDashboard() {
        return ResponseEntity.ok(Map.of("message", "Staff access granted"));
    }



    @GetMapping("/public/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
