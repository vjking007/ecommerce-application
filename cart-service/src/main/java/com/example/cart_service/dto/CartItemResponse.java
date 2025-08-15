package com.example.cart_service.dto;

import java.math.BigDecimal;

public record CartItemResponse(
        Long cartItemId,
        Long productId,
        Integer quantity,
        BigDecimal price,
        BigDecimal totalPrice
) {}
