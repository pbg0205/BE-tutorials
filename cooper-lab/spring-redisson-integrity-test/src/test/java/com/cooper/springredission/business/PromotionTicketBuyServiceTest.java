package com.cooper.springredission.business;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.exception.TicketSoldOutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PromotionTicketBuyServiceTest {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private DistributedLockPromotionTicketBuyFacade DistributedLockPromotionTicketBuyFacade;

    /**
     *
     * 테스트 코드에서 쓰레드 풀을 생성해서 테스트하니 ticket 의 insert 문이 생성되지 않는다.
     * <p>
     * 2. @Transactional : @Transactional 은 단일 쓰레드를 기반으로 동작하기 때문에 멀티 쓰레드 환경에서는 동작하지 않는다?
     */
    @Test
//    @Disabled
    @DisplayName("트랜잭션만 적용했을 때 동시성 테스트")
    void buyTicketTest01() throws InterruptedException {
        //given
        Promotion promotion = promotionRepository.save(new Promotion("프로모션1", 100));

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        //when
        for (int count = 0; count < numberOfThreads; count++) {
            executorService.submit(() -> {
                try {
                    DistributedLockPromotionTicketBuyFacade.buyTicket(promotion.getId()); // 여기서 ticket 생성이 되지 않는다.
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //then
        Promotion lookupPromotion = promotionRepository.findById(promotion.getId())
                .orElseThrow(() -> new TicketSoldOutException("프로모션의 티켓이 마감 되었습니다."));

        assertThat(lookupPromotion.getTicketAmount()).isZero();
        assertThat(lookupPromotion.soldOut()).isTrue();
    }

}
