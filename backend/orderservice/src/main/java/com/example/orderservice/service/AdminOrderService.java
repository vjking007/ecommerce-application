package com.example.orderservice.service;

//Generate Admin OrderService class in package com.example.orderservice.service
//This class will contain business logic for admin related order operations
//Add methods for viewing all orders, updating order status, and deleting orders
//Use OrderRepository for database operations
//Use OrderResponse as a return type for viewing orders
//Use OrderRequest as a parameter for updating orders
//Handle exceptions appropriately
//Use Spring's @Service annotation for the service class
//Use constructor injection for OrderRepository
import com.example.orderservice.dto.OrderItemResponse;
import com.example.orderservice.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.dto.OrderResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public AdminOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // View all orders
    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAllWithItems();

        return orders.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

//    // Update order status
//    public Optional<OrderResponse> updateOrderStatus(Long id, OrderRequest orderRequest) {
//        return orderRepository.findById(id).map(order -> {
//            order.setStatus(orderRequest.status());
//            OrderEntity updatedOrder = orderRepository.save(order);
//            return new OrderResponse(updatedOrder.getId(), updatedOrder.getStatus(), updatedOrder.getTotalAmount());
//        });
//    }

    //get order by id for admin view
    // View order by ID
    //Use List<OrderResponse> as return type
    //Fetch order by id using orderRepository
    //If order not found, throw RuntimeException with message "Order not found"
    //Convert Order entity to OrderResponse DTO
    //Return List<OrderResponse>
    public List<OrderResponse> getOrderById(Long id) {
        log.info("Fetching order with id: {}", id);
        List<Order> orders = orderRepository.findByIdWithItems(id);

        return orders.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Delete an order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
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
