package com.cooper.springredission.business;

import com.cooper.springredission.dto.TicketCreateResponse;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class DistributedLockPromotionTicketBuyFacade {

    private final PromotionTicketBuyService promotionTicketBuyService;
    private final RedissonClient redissonClient;

    public TicketCreateResponse buyTicket(final Long promotionId) {
        String key = "cooper";
        RLock rLock = redissonClient.getLock(key);

        try {
            rLock.tryLock(5000, 1000, TimeUnit.MILLISECONDS);
            return promotionTicketBuyService.buyTicket(promotionId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }
    }

}
