package com.example.paymentservice.dto;

import com.example.paymentservice.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private Long userId;
    private Double amount;
    private PaymentStatus status;
    private LocalDateTime paidAt;
}
