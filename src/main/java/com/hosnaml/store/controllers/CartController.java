package com.hosnaml.store.controllers;

import com.hosnaml.store.dtos.CartDto;
import com.hosnaml.store.dtos.CartItemDto;
import com.hosnaml.store.entities.Cart;
import com.hosnaml.store.mappers.CartItemMapper;
import com.hosnaml.store.mappers.CartMapper;
import com.hosnaml.store.repositories.CartItemRepository;
import com.hosnaml.store.repositories.CartRepository;
import com.hosnaml.store.repositories.ProductRepository;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {


    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    private Cart createCartIfNotExist(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setCreatedAt(LocalDateTime.now());
                    return cartRepository.save(newCart);
                });
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        var cart = createCartIfNotExist(userId);
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }


    @PostMapping("/{userId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable Long userId,
                                                 @RequestParam @NotNull Long productId,
                                                 @RequestParam  @Min(1) int quantity) {
        var cart = createCartIfNotExist(userId);
        var product = productRepository.findById(productId).orElse(null);
        if (product == null || quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }
        var cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> {
                    var newItem = new com.hosnaml.store.entities.CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(quantity);
                    newItem.setUnitPrice(product.getPrice());
                    return cartItemRepository.save(newItem);
                });
        return ResponseEntity.ok(cartItemMapper.toDto(cartItem));
    }
}
