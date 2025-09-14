package com.example.orderservice.controller;

//Generate a REST controller class AdminOrderController in package com.example.orderservice.controller
// with base URL /api/admin/orders. Use appropriate Spring annotations.
//Used OrderResponse and OrderRequest DTOs for request and response bodies.
// Use OrderService for business logic. Use @Autowired for dependency injection.
// Use ResponseEntity for responses. Implement endpoints for all CRUD operations.
// Use appropriate HTTP status codes for each operation.
// Use @PathVariable for id in endpoints and @RequestBody for Order in create and update endpoints.
// Use standard naming conventions for methods and variables.
import com.example.orderservice.service.AdminOrderService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.example.orderservice.dto.OrderResponse;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(adminOrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderResponse>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(adminOrderService.getOrderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        adminOrderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
