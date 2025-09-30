package com.hosnaml.store.services;

import com.hosnaml.store.dtos.ProductDto;
import com.hosnaml.store.entities.Product;
import com.hosnaml.store.mappers.ProductMapper;
import com.hosnaml.store.repositories.CategoryRepository;
import com.hosnaml.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<Product> listProducts(Byte categoryId) {
        return (categoryId == null)
                ? productRepository.findAllWithCategory()
                : productRepository.findByCategoryId(categoryId);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product create(ProductDto dto) {
        var category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        if (category == null) {
            return null; // signal bad request (invalid category)
        }
        Product product = productMapper.toEntity(dto);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product update(Long id, ProductDto dto) {
        var existing = productRepository.findById(id).orElse(null);
        if (existing == null) return null; // 404
        var category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        if (category == null) {
            throw new IllegalArgumentException("Category not found"); // 400
        }
        existing.setCategory(category);
        productMapper.update(dto, existing);
        return productRepository.save(existing);
    }

    public boolean delete(Long id) {
        var existing = productRepository.findById(id).orElse(null);
        if (existing == null) return false;
        productRepository.delete(existing);
        return true;
    }
}

