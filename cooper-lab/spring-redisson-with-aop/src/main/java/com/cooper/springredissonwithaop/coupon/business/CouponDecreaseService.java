package com.cooper.springredissonwithaop.coupon.business;

import com.cooper.springredissonwithaop.common.distributed_lock.annotation.DistributedLock;
import com.cooper.springredissonwithaop.coupon.domain.Coupon;
import com.cooper.springredissonwithaop.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouponDecreaseService {

    private final CouponRepository couponRepository;

    @Transactional
    public void couponDecrease(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(IllegalArgumentException::new);

        coupon.decrease();
    }

    @DistributedLock(key = "#lockName")
    public void couponDecrease(String lockName, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(IllegalArgumentException::new);

        coupon.decrease();
    }
}
