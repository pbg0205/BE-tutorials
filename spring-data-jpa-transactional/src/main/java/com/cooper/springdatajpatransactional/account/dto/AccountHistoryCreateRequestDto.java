package com.cooper.springdatajpatransactional.account.dto;

import com.cooper.springdatajpatransactional.account.domain.AccountHistory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountHistoryCreateRequestDto {

    private final String accountNumber;
    private final long balance;

    public static AccountHistoryCreateRequestDto create(String accountNumber, long balance) {
        return new AccountHistoryCreateRequestDto(accountNumber, balance);
    }

    public AccountHistory fromEntity() {
        return AccountHistory.create(this.accountNumber, this.balance);
    }
}
