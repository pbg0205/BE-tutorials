package com.cooper.springdatajpabasic.product.repository;

import com.cooper.springdatajpabasic.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")}) // time unit : milliseconds
    Product findByIdWithPessimisticLockRead(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")}) // time unit : milliseconds
    Product findByIdWithPessimisticLockWrite(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    @Lock(LockModeType.OPTIMISTIC)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")}) // time unit : milliseconds
    Product findByIdWithOptimisticLock(@Param("productId") Long productId);

}
