package com.cooper.springcoreevent.common.data;

import com.cooper.springcoreevent.account.domain.Account;
import com.cooper.springcoreevent.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        accountRepository.save(new Account("계좌1", 1000));
    }

}
