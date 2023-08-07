package com.cooper.springdatajpabasic.product.repository;

import com.cooper.springdatajpabasic.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
