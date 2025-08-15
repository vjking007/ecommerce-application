package com.example.cart_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AddItemRequest(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity,
        @NotNull BigDecimal price   // snapshot from Product service
) {}
