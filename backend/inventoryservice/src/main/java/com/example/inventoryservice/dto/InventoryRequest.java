package com.example.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryRequest {

    private Long productId;
    private Integer quantity; // for increase/decrease
}
