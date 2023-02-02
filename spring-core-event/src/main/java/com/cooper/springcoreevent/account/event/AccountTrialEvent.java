package com.cooper.springcoreevent.account.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AccountTrialEvent {

    private String name;
    private int money;

}
