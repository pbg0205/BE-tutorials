package com.cooper.springredission.business;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.domain.Ticket;
import com.cooper.springredission.domain.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SimplePromotionServiceTest {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @DisplayName("단일 인스턴스에 대한 동시성 테스트")
    void createTicketConsideringConcurrency() throws InterruptedException {
        //given
        Promotion promotion = promotionRepository.save(new Promotion("cooper concert 2022", 100L));

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(100);

        //when
        for (int threadCount = 1; threadCount <= 100; threadCount++) {
            executorService.submit(() -> {
                try {
                    promotionService.createTicket(promotion.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //then
        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(100);
    }

}
