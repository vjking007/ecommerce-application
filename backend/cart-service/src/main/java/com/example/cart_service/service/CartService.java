package com.example.cart_service.service;

import com.example.cart_service.dto.*;
import com.example.cart_service.entity.Cart;
import com.example.cart_service.entity.CartItem;
import com.example.cart_service.repository.CartItemRepository;
import com.example.cart_service.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .userId(userId)
                        .build()));
    }

    public CartResponse getCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return toResponse(cart);
    }

    @Transactional
    public CartResponse addItem(Long userId, AddItemRequest req) {
        Cart cart = getOrCreateCart(userId);

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), req.productId())
                .orElseGet(() -> {
                            CartItem newItem = CartItem.builder()
                                    .cart(cart)
                                    .productId(req.productId())
                                    .quantity(0)
                                    .price(req.price())
                                    .build();
                            cart.getItems().add(newItem);
                            return newItem;
                        });

        // If price changed in product service, you may decide to refresh here
        item.setPrice(req.price());
        item.setQuantity(item.getQuantity() + req.quantity());

        // Save the cart (will cascade save items because of CascadeType.ALL)
        Cart savedCart = cartRepository.save(cart);

        return toResponse(savedCart);
    }

    @Transactional
    public CartResponse updateItem(Long userId, UpdateItemRequest req) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), req.productId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));
        item.setQuantity(req.quantity());
        cartItemRepository.save(item);
        return toResponse(cartRepository.findById(cart.getId()).orElseThrow());
    }

    @Transactional
    public CartResponse removeItem(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .ifPresent(cartItemRepository::delete);
        return toResponse(cartRepository.findById(cart.getId()).orElseThrow());
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartId(cart.getId());
    }

    private CartResponse toResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(i -> new CartItemResponse(
                        i.getId(),
                        i.getProductId(),
                        i.getQuantity(),
                        i.getPrice(),
                        i.getTotalPrice()
                )).toList();

        BigDecimal subtotal = items.stream()
                .map(CartItemResponse::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(cart.getId(), cart.getUserId(), items, subtotal);
    }
}

