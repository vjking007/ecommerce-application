package com.example.orderservice.dto;

import com.example.orderservice.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private List<OrderItemResponse> items;
    private Double totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
}

