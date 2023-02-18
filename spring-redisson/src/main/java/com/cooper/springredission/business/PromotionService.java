package com.cooper.springredission.business;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.domain.Ticket;
import com.cooper.springredission.dto.PromotionCreateRequest;
import com.cooper.springredission.dto.TicketCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Transactional
    public TicketCreateResponse createTicket(final Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("promotion id not found"));

        promotion.addTicket(new Ticket(1000L));

        return new TicketCreateResponse(promotion.getId(), promotion.getName(), promotion.getTickets().size());
    }

    public String createPromotion(PromotionCreateRequest promotionCreateRequest) {
        Promotion promotion = new Promotion(promotionCreateRequest.getPromotionName());
        promotionRepository.save(promotion);
        return promotion.getName();
    }
}
