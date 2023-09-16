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
    void saveProductAndFindById() {
        Product savedProduct = productRepository.save(new Product("상품1", 1));
        Product lookupProduct = productRepository.findById(savedProduct.getId()).orElseThrow(RuntimeException::new);

        assertThat(lookupProduct.getName()).isEqualTo("상품1");
    }

    @Test
    @DisplayName("exist 메서드 쿼리 확인 - count 쿼리로 실행")
    void existById() {
        Product savedProduct = productRepository.save(new Product("상품1", 1));
        boolean exist = productRepository.existsById(savedProduct.getId());
        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("상품 저장 후 조회 시 비관적 락(shared lock) 동작 확인")
    void findByIdWithPessimisticLockRead() {
        Product savedProduct = productRepository.save(new Product("상품1", 1));
        Product lookupProduct = productRepository.findByIdWithPessimisticLockRead(savedProduct.getId());

        assertThat(lookupProduct.getName()).isEqualTo("상품1");
    }

    @Test
    @DisplayName("상품 저장 후 조회 시 비관적 락(exclusive lock) 동작 확인")
    void findByIdWithPessimisticLockWrite() {
        Product savedProduct = productRepository.save(new Product("상품1", 1));
        Product lookupProduct = productRepository.findByIdWithPessimisticLockWrite(savedProduct.getId());

        assertThat(lookupProduct.getName()).isEqualTo("상품1");
    }

    @Test
    @DisplayName("상품 저장 후 조회 시 낙관적 락(optimistic lock) 동작 확인")
    void findByIdWithOptimisticLock() {
        Product savedProduct = productRepository.save(new Product("상품1", 1));
        Product lookupProduct = productRepository.findByIdWithOptimisticLock(savedProduct.getId());

        assertThat(lookupProduct.getName()).isEqualTo("상품1");
    }

}
