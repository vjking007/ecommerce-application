package com.example.orderservice.service;

import com.example.orderservice.dto.*;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.exception.InsufficientStockException;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public OrderResponse createOrder(CreateOrderRequest request, Long userId) {
        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        // Step 1: Validate stock and calculate total
        for (OrderItemRequest itemRequest : request.getItems()) {
            ProductResponse product = productClient.getProductById(itemRequest.getProductId());

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            double itemTotal = product.getPrice() * itemRequest.getQuantity();
            total += itemTotal;

            items.add(OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .quantity(itemRequest.getQuantity())
                    .build());
        }

        // Step 2: Reduce stock, and rollback if failure
        try {
            for (OrderItem item : items) {
                inventoryClient.reduceStock(
                        new InventoryRequest(item.getProductId(), item.getQuantity())
                );
            }

            // Step 3: Create order
            Order order = Order.builder()
                    .userId(userId)
                    .items(items)
                    .totalPrice(total)
                    .status(OrderStatus.PENDING)
                    .build();

            // Set bi-directional relationship
            items.forEach(i -> i.setOrder(order));

            orderRepository.save(order);
            return toResponse(order);

        } catch (Exception ex) {
            // Rollback logic: increase stock
            for (OrderItem item : items) {
                try {
                    inventoryClient.increaseStock(
                            new InventoryRequest(item.getProductId(), item.getQuantity())
                    );
                } catch (Exception rollbackEx) {
                    // log rollback failure
                    System.err.println("Failed to rollback stock for product: " + item.getProductId());
                }
            }

            // Rethrow or handle exception
            throw new RuntimeException("Order creation failed. Rolled back stock. Reason: " + ex.getMessage());
        }
    }


    @Transactional
    public List<OrderResponse> getOrdersForUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

//        if (!order.getUserId().equals(userId)) {
//            throw new UnauthorizedAccessException("You are not allowed to view this order");
//        }
        order.getItems().size();  // Access to force loading

        return toResponse(order);
    }


    private OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(i -> OrderItemResponse.builder()
                                .productId(i.getProductId())
                                .productName(i.getProductName())
                                .productPrice(i.getProductPrice())
                                .quantity(i.getQuantity())
                                .build())
                        .toList())
                .build();
    }
}

