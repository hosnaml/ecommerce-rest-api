package com.hosnaml.store.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2,max = 100, message = "Product name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Product description is required")
    @Size(min = 10,max = 1000, message = "Product description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Product price must be a valid monetary amount")
    private BigDecimal price;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be a positive number")
    private Byte categoryId;
}
