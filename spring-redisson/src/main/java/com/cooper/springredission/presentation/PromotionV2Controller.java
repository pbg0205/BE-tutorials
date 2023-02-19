package com.cooper.springredission.presentation;

import com.cooper.springredission.business.PromotionService;
import com.cooper.springredission.dto.PromotionCreateRequest;
import com.cooper.springredission.dto.PromotionCreateResponse;
import com.cooper.springredission.dto.TicketCreateResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/promotions")
public class PromotionV2Controller {

    private final PromotionService promotionService;

    public PromotionV2Controller(@Qualifier("redissonPromotionService") PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping
    public ResponseEntity<PromotionCreateResponse> createPromotion(@RequestBody PromotionCreateRequest promotionCreateRequest) {
        PromotionCreateResponse promotionCreateResponse = promotionService.createPromotion(promotionCreateRequest);
        return ResponseEntity.ok(promotionCreateResponse);
    }

    @PostMapping("/{promotionId}/ticket")
    public ResponseEntity<TicketCreateResponse> createTicket(@PathVariable Long promotionId) {
        TicketCreateResponse ticketCreateResponse = promotionService.createTicket(promotionId);
        return ResponseEntity.ok(ticketCreateResponse);
    }

}
