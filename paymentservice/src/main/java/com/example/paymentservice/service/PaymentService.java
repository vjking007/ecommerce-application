package com.example.paymentservice.service;

import com.example.paymentservice.dto.OrderDTO;
import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    public PaymentResponse processPayment(PaymentRequest request) {
        // Validate order (you can use Feign or assume values)
        OrderDTO order = orderClient.getOrderById(request.getOrderId(), request.getUserId());

        if (!Objects.equals(order.getId(), request.getUserId())) {
            throw new RuntimeException("Order does not belong to user");
        }

        if (!Objects.equals(order.getTotalPrice(), request.getAmount())) {
            throw new RuntimeException("Amount mismatch");
        }

        // Dummy success payment
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status(PaymentStatus.SUCCESS) // simulate success
                .build();

        paymentRepository.save(payment);

        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paidAt(payment.getPaidAt())
                .build();
    }

    public List<PaymentResponse> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(payment -> PaymentResponse.builder()
                        .id(payment.getId())
                        .orderId(payment.getOrderId())
                        .userId(payment.getUserId())
                        .amount(payment.getAmount())
                        .status(payment.getStatus())
                        .paidAt(payment.getPaidAt())
                        .build())
                .collect(Collectors.toList());
    }
}

