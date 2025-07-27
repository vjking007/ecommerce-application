package com.example.orderservice.service;

import com.example.orderservice.dto.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "http://localhost:8085")
public interface InventoryClient {
    @PostMapping("/api/inventory/reduce")
    void reduceStock(@RequestBody InventoryRequest request);
}

