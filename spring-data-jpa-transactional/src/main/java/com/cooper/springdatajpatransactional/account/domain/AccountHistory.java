package com.cooper.springdatajpatransactional.account.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", updatable = false)
    private String accountNumber;

    @Column(name = "current_balance")
    private long currentBalance;

    private AccountHistory(String accountNumber, long currentBalance) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
    }

    public static AccountHistory create (String accountNumber, long currentBalance) {
        return new AccountHistory(accountNumber, currentBalance);
    }

}
