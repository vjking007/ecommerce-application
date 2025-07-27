package com.example.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryResponse {
    private Long productId;
    private Integer quantity;
    private boolean inStock;
    private LocalDateTime lastUpdated;
}

