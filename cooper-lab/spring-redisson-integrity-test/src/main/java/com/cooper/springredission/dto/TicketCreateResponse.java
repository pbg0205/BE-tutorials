package com.cooper.springredission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TicketCreateResponse {

    private final Long promotionId;
    private final String promotionName;
    private final int remainingTickets;

}
