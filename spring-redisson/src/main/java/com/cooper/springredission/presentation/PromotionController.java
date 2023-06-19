package com.cooper.springredission.presentation;

import com.cooper.springredission.business.PromotionService;
import com.cooper.springredission.dto.PromotionCreateRequest;
import com.cooper.springredission.dto.PromotionCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/v1/promotions")
    public ResponseEntity<PromotionCreateResponse> createPromotion(@RequestBody PromotionCreateRequest promotionCreateRequest) {
        PromotionCreateResponse promotionCreateResponse = promotionService.createPromotion(promotionCreateRequest);
        return ResponseEntity.ok(promotionCreateResponse);
    }

}
