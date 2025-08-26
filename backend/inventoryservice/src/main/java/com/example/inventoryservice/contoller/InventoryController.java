package com.example.inventoryservice.contoller;

import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getStock(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getProductStock(productId));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addStock(@RequestBody InventoryRequest request) {
        inventoryService.addStock(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reduce")
    public ResponseEntity<Void> reduceStock(@RequestBody InventoryRequest request) {
        inventoryService.reduceStock(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/increase")
    public ResponseEntity<Void> increaseStock(@RequestBody InventoryRequest request) {
        inventoryService.increaseStock(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }
}
