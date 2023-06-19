package com.cooper.springredission.business;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.dto.PromotionCreateRequest;
import com.cooper.springredission.dto.PromotionCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionCreateResponse createPromotion(PromotionCreateRequest promotionCreateRequest) {
        Promotion promotion = new Promotion(
                promotionCreateRequest.getPromotionName(),
                promotionCreateRequest.getTicketMaxLimit());

        Promotion savedPromotion = promotionRepository.save(promotion);

        return new PromotionCreateResponse(
                savedPromotion.getId(),
                savedPromotion.getName(),
                savedPromotion.getTicketAmount()
        );
    }

}
