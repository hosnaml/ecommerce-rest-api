package com.hosnaml.store.mappers;

import com.hosnaml.store.dtos.CartDto;
import com.hosnaml.store.entities.Cart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {

    CartDto toDto(Cart cart);

    @AfterMapping
    default void calculateTotal(Cart cart, @MappingTarget CartDto dto) {
        if (cart == null || cart.getItems() == null) {
            dto.setTotal(BigDecimal.ZERO);
            return;
        }

        BigDecimal total = cart.getItems().stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotal(total);
    }
}
