package com.hosnaml.store.mappers;

import com.hosnaml.store.dtos.ProductDto;
import com.hosnaml.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);
}
