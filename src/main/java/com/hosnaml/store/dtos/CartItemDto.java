package com.hosnaml.store.dtos;

import jakarta.validation.constraints.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Data
public class CartItemDto {

    private Long id;

    @NotNull
    @Positive(message = "productId must be positive")
    private Long productId;

    private String productName;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Product price must be a valid monetary amount")
    private BigDecimal unitPrice;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private BigDecimal subtotal; // unitPrice * quantity

}
