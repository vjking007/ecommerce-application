package com.example.paymentservice.service;

import com.example.paymentservice.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order-service", url = "http://localhost:8083")
public interface OrderClient {

    @GetMapping("/api/orders/{orderId}")
    OrderDTO getOrderById(@PathVariable("orderId") Long orderId, @RequestHeader("X-USER-ID") Long userId);
}

