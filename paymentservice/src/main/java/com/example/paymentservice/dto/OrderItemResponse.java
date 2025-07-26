package com.example.paymentservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer quantity;
}
