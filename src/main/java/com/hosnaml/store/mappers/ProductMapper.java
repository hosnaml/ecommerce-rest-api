package com.hosnaml.store.mappers;

import com.hosnaml.store.dtos.product.AddProductRequest;
import com.hosnaml.store.dtos.product.ProductDto;
import com.hosnaml.store.dtos.product.UpdatedProductRequest;
import com.hosnaml.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);
    Product toEntity(AddProductRequest productDto);
    void update(UpdatedProductRequest request, @MappingTarget Product product);
}
