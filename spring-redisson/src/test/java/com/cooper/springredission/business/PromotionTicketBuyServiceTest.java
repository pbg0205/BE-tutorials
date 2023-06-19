package com.cooper.springredission.business;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.domain.Ticket;
import com.cooper.springredission.domain.TicketRepository;
import com.cooper.springredission.exception.TicketSoldOutException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PromotionTicketBuyServiceTest {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    @Qualifier("promotionTicketBuyService")
    private PromotionTicketBuyService promotionTicketBuyService;

    /**
     *
     * 테스트 코드에서 쓰레드 풀을 생성해서 테스트하니 ticket 의 insert 문이 생성되지 않는다.
     * <p>
     * 1. @SpringBootTest + @Transactional : ticket 의 insert 가 동작하지 않는다.
     * <p>
     * 2. @SpringBootTest : ticket 의 insert 는 활성화 되지만 no-session 이 발생함.
     * <p>
     * - 영속성 컨텍스트가 @Transactional 내에서 동작하므로 no-session 이 발생하는 것은 불가피함.
     */
    @Test
    @Disabled
    @DisplayName("트랜잭션만 적용했을 때 동시성 테스트")
    void buyTicketTest01() {
        //given
        Promotion promotion = promotionRepository.save(new Promotion("프로모션1", 100));

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        //when
        for (int count = 0; count < numberOfThreads; count++) {
            executorService.submit(() -> {
                promotionTicketBuyService.buyTicket(promotion.getId()); // 여기서 ticket 생성이 되지 않는다.
            });
        }

        //then
        Promotion lookupPromotion = promotionRepository.findById(promotion.getId())
                .orElseThrow(() -> new TicketSoldOutException("프로모션의 티켓이 마감 되었습니다."));

        System.out.println("lookupPromotion = " + lookupPromotion);
        assertThat(lookupPromotion.getTicketAmount()).isPositive();
        assertThat(lookupPromotion.soldOut()).isFalse();
    }

    @Test
    void ticketTest() {
        //given
        Promotion promotion = promotionRepository.save(new Promotion("프로모션1", 100));

        //when
        promotion.addTicket(new Ticket(1000L));

        //then
        assertThat(ticketRepository.findAll()).hasSize(1);
    }

}
