package com.hosnaml.store.mappers;

import com.hosnaml.store.dtos.ProductDto;
import com.hosnaml.store.entities.Category;
import com.hosnaml.store.entities.Product;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-25T16:18:41+0200",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Byte categoryId = null;
        Long id = null;
        String name = null;
        String description = null;
        BigDecimal price = null;

        categoryId = productCategoryId( product );
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();

        ProductDto productDto = new ProductDto( id, name, description, price, categoryId );

        return productDto;
    }

    private Byte productCategoryId(Product product) {
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getId();
    }
}
