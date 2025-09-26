package com.hosnaml.store.dtos.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;
}
