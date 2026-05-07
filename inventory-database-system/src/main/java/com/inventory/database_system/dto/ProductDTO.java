package com.inventory.database_system.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private Integer reorderLevel;
    private String description;
    private Boolean active;
    private String createdAt;
    private String updatedAt;

    // Flattened relationship fields
    private Long categoryId;
    private String categoryName;
    private Long supplierId;
    private String supplierName;
}
