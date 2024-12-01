package com.cooper.springkafka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class Greeting {
    private String name;
    private String message;
}
