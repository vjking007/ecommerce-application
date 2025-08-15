package com.example.cart_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateItemRequest(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity
) {}
