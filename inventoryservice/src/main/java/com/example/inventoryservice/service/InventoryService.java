package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.exception.InsufficientStockException;
import com.example.inventoryservice.exception.ProductNotFoundException;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryResponse getProductStock(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found in inventory"));

        return InventoryResponse.builder()
                .productId(productId)
                .quantity(inventory.getQuantity())
                .inStock(inventory.getQuantity() > 0)
                .lastUpdated(inventory.getLastUpdated())
                .build();
    }

    public void reduceStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (inventory.getQuantity() < quantity) {
            throw new InsufficientStockException("Not enough stock");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }

    public void addStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElse(
                        Inventory.builder().productId(productId).quantity(0).build()
                );

        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
    }
}

