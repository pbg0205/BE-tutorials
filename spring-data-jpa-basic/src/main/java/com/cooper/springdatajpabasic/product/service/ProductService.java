package com.cooper.springdatajpabasic.product.service;

import com.cooper.springdatajpabasic.product.domain.Product;
import com.cooper.springdatajpabasic.product.dto.ProductCreateRequestDto;
import com.cooper.springdatajpabasic.product.dto.ProductCreateResponseDto;
import com.cooper.springdatajpabasic.product.dto.ProductLookupDto;
import com.cooper.springdatajpabasic.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductLookupDto findById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new);

        for (int i = 0; i < 10; i++) {
            productRepository.findById(productId).orElseThrow(RuntimeException::new);
        }

        ProductLookupDto productLookupDto = new ProductLookupDto(product.getId(), product.getName(), product.getQuantity());
        return productLookupDto;
    }

    @Transactional
    public ProductCreateResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Product product = new Product(productCreateRequestDto.getName(), productCreateRequestDto.getQuantity());
        Product savedProduct = productRepository.save(product);
        return new ProductCreateResponseDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getQuantity());
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
