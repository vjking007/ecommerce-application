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

        Order order = Order.builder()
                .userId(userId)
                .items(items)
                .totalPrice(total)
                .status(OrderStatus.PENDING)
                .build();

        // Set bi-directional
        items.forEach(i -> i.setOrder(order));

        orderRepository.save(order);

        //Reduce stock for each product
        for (OrderItem item : items) {
            inventoryClient.reduceStock(
                    new InventoryRequest(item.getProductId(), item.getQuantity())
            );
        }

        return toResponse(order);
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

