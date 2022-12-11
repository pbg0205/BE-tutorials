package com.cooper.springdatajpatransactional.account.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", updatable = false, nullable = false)
    private String accountNumber;

    @Column(name = "password", updatable = false, nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    @ColumnDefault("0")
    private long balance;

    private Account(String accountNumber, String password, long balance) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = balance;
    }

    public static Account create(String accountNumber, String password, long balance) {
        return new Account(accountNumber, password, balance);
    }

}
