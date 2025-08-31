package com.example.orderservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private double price;

    private int stock;

    private String category;

    private boolean active;
}

