package com.hosnaml.store.mappers;

import com.hosnaml.store.dtos.product.AddProductRequest;
import com.hosnaml.store.dtos.product.ProductDto;
import com.hosnaml.store.dtos.product.UpdatedProductRequest;
import com.hosnaml.store.entities.Category;
import com.hosnaml.store.entities.Product;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-26T15:20:25+0200",
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

    @Override
    public Product toEntity(AddProductRequest productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( productDto.getName() );
        product.description( productDto.getDescription() );
        product.price( productDto.getPrice() );

        return product.build();
    }

    @Override
    public void update(UpdatedProductRequest request, Product product) {
        if ( request == null ) {
            return;
        }

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
    }

    private Byte productCategoryId(Product product) {
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getId();
    }
}
