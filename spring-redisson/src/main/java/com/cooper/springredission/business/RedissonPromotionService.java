package com.cooper.springredission.business;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.domain.Ticket;
import com.cooper.springredission.dto.PromotionCreateRequest;
import com.cooper.springredission.dto.PromotionCreateResponse;
import com.cooper.springredission.dto.TicketCreateResponse;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedissonPromotionService implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final RedissonClient redissonClient;

    @Override
    @Transactional
    public TicketCreateResponse createTicket(final Long promotionId) {
        String key = "cooper";
        RLock lock = redissonClient.getLock(key);

        try {
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("lock 획득 실패");
            }

            return createTicketWithPromotionId(promotionId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    private TicketCreateResponse createTicketWithPromotionId(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("promotion id not found"));

        if (promotion.isClosed()) {
            throw new RuntimeException("promotion is closed");
        }

        Ticket ticket = new Ticket(1000L);
        promotion.addTicket(ticket);

        return new TicketCreateResponse(promotion.getId(), promotion.getName(), promotion.getTickets().size());
    }

    @Override
    public PromotionCreateResponse createPromotion(PromotionCreateRequest promotionCreateRequest) {
        Promotion promotion = new Promotion(
                promotionCreateRequest.getPromotionName(),
                promotionCreateRequest.getTicketMaxLimit());

        promotionRepository.save(promotion);
        return new PromotionCreateResponse(promotion.getName(), promotion.getTicketMaxLimit());
    }

}