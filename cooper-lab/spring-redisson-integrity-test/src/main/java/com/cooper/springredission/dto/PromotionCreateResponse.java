package com.cooper.springredission.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PromotionCreateResponse {

    private final Long promotionId;
    private final String promotionName;
    private final int ticketMaxLimit;

}
