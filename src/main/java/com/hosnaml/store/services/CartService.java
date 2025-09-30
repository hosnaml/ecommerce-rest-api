package com.hosnaml.store.services;

import com.hosnaml.store.entities.Cart;
import com.hosnaml.store.entities.CartItem;
import com.hosnaml.store.entities.Product;
import com.hosnaml.store.repositories.CartItemRepository;
import com.hosnaml.store.repositories.CartRepository;
import com.hosnaml.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public Cart getOrCreate(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUserId(userId);
                    c.setCreatedAt(LocalDateTime.now());
                    return cartRepository.save(c);
                });
    }

    public CartItem addItem(Long userId, Long productId, int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be >= 1");
        Cart cart = getOrCreate(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + quantity);
                    return existing; // managed entity auto-flushed
                })
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setCart(cart);
                    ci.setProduct(product);
                    ci.setQuantity(quantity);
                    ci.setUnitPrice(product.getPrice());
                    return cartItemRepository.save(ci);
                });
    }

    public CartItem updateQuantity(Long userId, Long productId, int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be >= 1");
        Cart cart = getOrCreate(userId);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        item.setQuantity(quantity); // managed; flush on commit
        return item;
    }

    public void removeItem(Long userId, Long productId) {
        Cart cart = getOrCreate(userId);
        cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .ifPresent(cartItemRepository::delete);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) return;
        // Use orphanRemoval by clearing the managed collection (keeps persistence context consistent)
        if (!cart.getItems().isEmpty()) {
            cart.getItems().clear();
        }
    }
}

