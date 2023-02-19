package com.cooper.springredission.business;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.domain.Ticket;
import com.cooper.springredission.dto.PromotionCreateRequest;
import com.cooper.springredission.dto.PromotionCreateResponse;
import com.cooper.springredission.dto.TicketCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SimplePromotionService implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public TicketCreateResponse createTicket(final Long promotionId) {
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
