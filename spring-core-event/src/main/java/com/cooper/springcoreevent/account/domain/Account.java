package com.cooper.springcoreevent.account.domain;

import com.cooper.springcoreevent.account.exception.InvalidMoneyException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int money;

    public Account(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public void deposit(int money) {
        this.money += money;
    }

    public void withdraw(int money) {
        if (this.money - money < 0) {
            throw new InvalidMoneyException(this.money, money);
        }
        this.money -= money;
    }
}
