package com.cooper.springdatajpabasic.product.repository;

import com.cooper.springdatajpabasic.annotation.RepositorySlicingTest;
import com.cooper.springdatajpabasic.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@RepositorySlicingTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장한다")
    void saveProduct() {
        Product savedProduct = productRepository.save(new Product("상품1", 1));
        assertThat(savedProduct.getName()).isEqualTo("상품1");
    }

    @Test
    @DisplayName("상품 저장 후 조회 시 동작 확인")
    void saveProductAnd() {
        Product savedProduct = productRepository.save(new Product("상품1", 1));
        Product lookupProduct = productRepository.findById(savedProduct.getId()).orElseThrow(RuntimeException::new);

        assertThat(lookupProduct.getName()).isEqualTo("상품1");
    }



}
