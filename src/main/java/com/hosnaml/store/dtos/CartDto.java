package com.hosnaml.store.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Data
public class CartDto {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    private LocalDateTime createdAt;

    private List<CartItemDto> items = new ArrayList<>();

    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Total price must be a valid monetary amount")
    private BigDecimal total;
}
