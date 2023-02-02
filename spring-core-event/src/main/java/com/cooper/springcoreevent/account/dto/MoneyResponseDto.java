package com.cooper.springcoreevent.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyResponseDto {

    private long id;
    private String name;
    private int money;

    public static MoneyResponseDto of(long id, String name, int money) {
        return new MoneyResponseDto(id, name, money);
    }

}
