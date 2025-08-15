package com.example.cart_service.controller;

import com.example.cart_service.dto.*;
import com.example.cart_service.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "Cart operations for a user")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // NOTE: Prod me userId JWT se nikaalna chahiye; yahan header se le rahe hain.
    private Long resolveUserId(String header) {
        if (header == null) throw new IllegalArgumentException("X-User-Id header required");
        return Long.parseLong(header);
    }

    @Operation(summary = "Get current cart for user")
    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestHeader("X-User-Id") String user) {
        return ResponseEntity.ok(cartService.getCart(resolveUserId(user)));
    }

    @Operation(summary = "Add item to cart",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CartResponse.class))))
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addItem(@RequestHeader("X-User-Id") String user,
                                                @Valid @RequestBody AddItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(resolveUserId(user), request));
    }

    @Operation(summary = "Update existing item quantity in cart")
    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateItem(@RequestHeader("X-User-Id") String user,
                                                   @Valid @RequestBody UpdateItemRequest request) {
        return ResponseEntity.ok(cartService.updateItem(resolveUserId(user), request));
    }

    @Operation(summary = "Remove an item from cart by productId")
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartResponse> removeItem(@RequestHeader("X-User-Id") String user,
                                                   @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItem(resolveUserId(user), productId));
    }

    @Operation(summary = "Clear cart")
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clear(@RequestHeader("X-User-Id") String user) {
        cartService.clearCart(resolveUserId(user));
        return ResponseEntity.noContent().build();
    }
}

