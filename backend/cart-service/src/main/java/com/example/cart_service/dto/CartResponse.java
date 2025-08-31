package com.example.cart_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        Long cartId,
        Long userId,
        List<CartItemResponse> items,
        BigDecimal subTotal
) {}
