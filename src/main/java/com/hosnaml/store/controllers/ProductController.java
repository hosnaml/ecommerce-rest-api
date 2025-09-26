package com.hosnaml.store.controllers;

import com.hosnaml.store.mappers.ProductMapper;
import com.hosnaml.store.repositories.CategoryRepository;
import com.hosnaml.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hosnaml.store.dtos.product.ProductDto;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping()
    public Iterable<ProductDto> getAllProducts(
            @Valid @RequestParam(required = false, name = "categoryId") Byte categoryId
    ) {
        var products = (categoryId == null)
               ? productRepository.findAllWithCategory()
               : productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody ProductDto request,
            UriComponentsBuilder uriBuilder) {
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);
        request.setId(product.getId());
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(request.getId()).toUri();
        return ResponseEntity.created(uri).body(request);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody  ProductDto request) {
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }

        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setCategory(category);
        productMapper.update(request, product);
        productRepository.save(product);
        request.setId(product.getId());
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable (name = "id") Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

}
