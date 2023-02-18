package com.cooper.springredission.presentation;

import com.cooper.springredission.business.PromotionService;
import com.cooper.springredission.dto.PromotionCreateRequest;
import com.cooper.springredission.dto.PromotionCreateResponse;
import com.cooper.springredission.dto.TicketCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<PromotionCreateResponse> createPromotion(@RequestBody PromotionCreateRequest promotionCreateRequest) {
        String promotionName = promotionService.createPromotion(promotionCreateRequest);
        return ResponseEntity.ok(new PromotionCreateResponse(promotionName));
    }

    @PostMapping("/{promotionId}/ticket")
    public ResponseEntity<TicketCreateResponse> createTicket(@PathVariable Long promotionId) {
        TicketCreateResponse ticketCreateResponse = promotionService.createTicket(promotionId);
        return ResponseEntity.ok(ticketCreateResponse);
    }

}
