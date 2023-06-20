package com.cooper.springredission.business;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.domain.Ticket;
import com.cooper.springredission.domain.TicketRepository;
import com.cooper.springredission.dto.TicketCreateResponse;
import com.cooper.springredission.exception.PromotionNotFoundException;
import com.cooper.springredission.exception.TicketSoldOutException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionTicketBuyService {

    private final PromotionRepository promotionRepository;
    private final TicketRepository ticketRepository;
    private final RedissonClient redissonClient;

    public TicketCreateResponse buyTicket(final Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionNotFoundException("요청하신 프로모션이 존재하지 않습니다."));

        if (promotion.soldOut()) {
            throw new TicketSoldOutException("프로모션의 티켓이 마감 되었습니다.");
        }

        Ticket ticket = new Ticket(1000L, promotion.getId());
        ticketRepository.save(ticket);
        promotion.decreaseTicketAmount();

        return new TicketCreateResponse(promotion.getId(), promotion.getName(), promotion.remainingTickets());
    }

    public TicketCreateResponse buyTicketWithRLockInTx(final Long promotionId) {
        String key = "cooper";
        RLock rLock = redissonClient.getLock(key);

        try {
            rLock.tryLock(5000, 1000, TimeUnit.MILLISECONDS);

            Promotion promotion = promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new PromotionNotFoundException("요청하신 프로모션이 존재하지 않습니다."));

            if (promotion.soldOut()) {
                throw new TicketSoldOutException("프로모션의 티켓이 마감 되었습니다.");
            }

            Ticket ticket = new Ticket(1000L, promotion.getId());
            ticketRepository.save(ticket);
            promotion.decreaseTicketAmount();

            return new TicketCreateResponse(promotion.getId(), promotion.getName(), promotion.remainingTickets());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }

    }

}
