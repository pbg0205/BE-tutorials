package com.cooper.springredission.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PromotionCreateResponse {

    private final String promotionName;
    private final Long ticketMaxLimit;

}
