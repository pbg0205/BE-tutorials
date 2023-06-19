package com.cooper.springredission.presentation;

import com.cooper.springredission.business.DistributedLockPromotionTicketBuyFacade;
import com.cooper.springredission.business.PromotionService;
import com.cooper.springredission.business.PromotionTicketBuyService;
import com.cooper.springredission.dto.TicketCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketController {

    private final PromotionTicketBuyService promotionTicketBuyService;
    private final DistributedLockPromotionTicketBuyFacade distributedLockPromotionTicketBuyFacade;

    @PostMapping("/v1/promotions/{promotionId}/ticket")
    public ResponseEntity<TicketCreateResponse> createTicketVer01(@PathVariable Long promotionId) {
        TicketCreateResponse ticketCreateResponse = promotionTicketBuyService.buyTicket(promotionId);
        System.out.println("ticketCreateResponse = " + ticketCreateResponse);
        return ResponseEntity.ok(ticketCreateResponse);
    }

    @PostMapping("/v2/promotions/{promotionId}/ticket")
    public ResponseEntity<TicketCreateResponse> createTicketVer02(@PathVariable Long promotionId) {
        TicketCreateResponse ticketCreateResponse = promotionTicketBuyService.buyTicketWithRLockInTx(promotionId);
        System.out.println("ticketCreateResponse = " + ticketCreateResponse);
        return ResponseEntity.ok(ticketCreateResponse);
    }

    @PostMapping("/v3/promotions/{promotionId}/ticket")
    public ResponseEntity<TicketCreateResponse> createTicketVer03(@PathVariable Long promotionId) {
        TicketCreateResponse ticketCreateResponse = distributedLockPromotionTicketBuyFacade.buyTicket(promotionId);
        System.out.println("ticketCreateResponse = " + ticketCreateResponse);
        return ResponseEntity.ok(ticketCreateResponse);
    }

}
