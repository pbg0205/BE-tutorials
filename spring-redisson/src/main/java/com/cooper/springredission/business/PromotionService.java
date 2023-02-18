package com.cooper.springredission.business;

import com.cooper.springredission.dto.PromotionCreateRequest;
import com.cooper.springredission.dto.PromotionCreateResponse;
import com.cooper.springredission.dto.TicketCreateResponse;

public interface PromotionService {

    TicketCreateResponse createTicket(final Long promotionId);

    PromotionCreateResponse createPromotion(PromotionCreateRequest promotionCreateRequest);

}
