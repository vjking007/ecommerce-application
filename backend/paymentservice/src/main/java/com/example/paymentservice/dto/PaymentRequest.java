package com.example.paymentservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private Long userId;
    private Double amount;
}

