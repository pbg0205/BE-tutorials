package com.cooper.springredission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketCreateResponse {

    private final Long promotionId;
    private final String promotionName;
    private final int ticketSize;

}
