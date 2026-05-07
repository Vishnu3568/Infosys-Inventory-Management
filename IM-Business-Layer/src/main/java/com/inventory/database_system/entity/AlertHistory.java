package com.inventory.database_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "alert_history")
@Data
public class AlertHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String userRole;
    private LocalDateTime timestamp;
    private Integer itemsCount;

    public AlertHistory() {}

    public AlertHistory(String username, String userRole, LocalDateTime timestamp, Integer itemsCount) {
        this.username = username;
        this.userRole = userRole;
        this.timestamp = timestamp;
        this.itemsCount = itemsCount;
    }
}
