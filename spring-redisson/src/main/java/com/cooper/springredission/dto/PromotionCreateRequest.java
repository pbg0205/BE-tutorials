package com.cooper.springredission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionCreateRequest {

    private String promotionName;
    private Long ticketMaxLimit;

}
