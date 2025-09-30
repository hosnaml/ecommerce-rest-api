package com.hosnaml.store.controllers;

import com.hosnaml.store.dtos.CartDto;
import com.hosnaml.store.dtos.CartItemDto;
import com.hosnaml.store.mappers.CartItemMapper;
import com.hosnaml.store.mappers.CartMapper;
import com.hosnaml.store.services.CartService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        var cart = cartService.getOrCreate(userId);
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable Long userId,
                                                 @RequestParam @NotNull Long productId,
                                                 @RequestParam @Min(1) int quantity) {
        var item = cartService.addItem(userId, productId, quantity);
        return ResponseEntity.ok(cartItemMapper.toDto(item));
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable Long userId,
                                                      @PathVariable Long productId,
                                                      @RequestParam @NotNull @Min(1) int quantity) {
        try {
            var item = cartService.updateQuantity(userId, productId, quantity);
            return ResponseEntity.ok(cartItemMapper.toDto(item));
        } catch (IllegalArgumentException ex) {
            // Differentiate not found vs invalid quantity via message
            if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long userId,
                                               @PathVariable Long productId) {
        cartService.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
