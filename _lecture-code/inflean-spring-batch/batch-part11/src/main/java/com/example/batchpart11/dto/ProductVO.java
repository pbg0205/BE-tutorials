package com.example.batchpart11.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ProductVO {

    private Long id;
    private String name;
    private int price;
    private String type;

}
