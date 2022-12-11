package com.cooper.springdatajpatransactional.account.service;

import com.cooper.springdatajpatransactional.account.domain.Account;
import com.cooper.springdatajpatransactional.account.domain.AccountHistory;
import com.cooper.springdatajpatransactional.account.repository.AccountHistoryRepository;
import com.cooper.springdatajpatransactional.account.repository.AccountRepository;
import com.cooper.springdatajpatransactional.account.service.required_new.AccountRequiredNewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountRequiredNewServiceTest {

    @Autowired
    private AccountRequiredNewService accountRequiredNewService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @BeforeEach
    void init() {
        accountRepository.deleteAll();
        accountHistoryRepository.deleteAll();
    }

    @DisplayName("required_new 일 경우, 하나의 트랜잭션으로 묶인다")
    @Test
    void saveAccountWhenHistoryRequired() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        // if transaction commit,  Account 1 + AccountHistory 1
        accountRequiredNewService.saveAccountWhenHistoryRequiredNew(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);
    }

    @DisplayName("required_new 일 경우, 자식 트랜잭션에서 예외가 발생하면 자식 트랜잭션만 반영되지 않는다.")
    @Test
    void throwExceptionWhenRequired() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        //if transaction commit,  Account 1 + AccountHistory 1
        accountRequiredNewService.saveAccountWhenHistoryThrowException(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(0);
    }

}
