package com.example.paymentservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private List<OrderItemResponse> items;
    private Double totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
