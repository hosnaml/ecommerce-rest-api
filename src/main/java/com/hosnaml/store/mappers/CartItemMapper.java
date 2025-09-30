package com.hosnaml.store.mappers;

import com.hosnaml.store.dtos.CartItemDto;
import com.hosnaml.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "subtotal", expression = "java(cartItem.getUnitPrice().multiply(java.math.BigDecimal.valueOf(cartItem.getQuantity())))")
    CartItemDto toDto(CartItem cartItem);

    CartItem toEntity(CartItemDto cartItemDto);

}
