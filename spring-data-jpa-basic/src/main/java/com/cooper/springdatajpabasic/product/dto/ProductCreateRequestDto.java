package com.cooper.springdatajpabasic.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequestDto {

    private String name;
    private int quantity;

}
