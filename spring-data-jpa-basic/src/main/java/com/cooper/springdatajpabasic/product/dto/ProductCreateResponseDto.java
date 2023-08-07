package com.cooper.springdatajpabasic.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductCreateResponseDto {

    private final Long id;
    private final String name;
    private final int quantity;

}
