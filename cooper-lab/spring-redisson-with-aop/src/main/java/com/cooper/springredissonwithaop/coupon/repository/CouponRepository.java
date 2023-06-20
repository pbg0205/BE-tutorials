package com.cooper.springredissonwithaop.coupon.repository;

import com.cooper.springredissonwithaop.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
