package com.cooper.springdatajpatransactional.account.service;

import com.cooper.springdatajpatransactional.account.domain.Account;
import com.cooper.springdatajpatransactional.account.domain.AccountHistory;
import com.cooper.springdatajpatransactional.account.repository.AccountHistoryRepository;
import com.cooper.springdatajpatransactional.account.repository.AccountRepository;
import com.cooper.springdatajpatransactional.account.service.not_supported.AccountNotSupportedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountNotSupportedServiceTest {

    @Autowired
    private AccountNotSupportedService accountNotSupportedService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @DisplayName("not_supported 일 경우, 부모 트랜잭션이 존재하면 일시정지한다")
    @Test
    void saveAccountWenHistoryNotSupported() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        // if transaction commit,  Account 1 + AccountHistory 1
        /*
        * 1. 부모 트랜잭션이 존재할 경우, 부모 트랜잭션을 멈춘다.
        * 2. 자식 트랜잭션은 동작하고 커밋한다.
        * 3. 정지했던 부모 트랜잭션을 회복하여 부모 트랙잭션을 진행한다. (자식이 완료되고 부모가 다시 트랜잭션이 동작하는걸로 보아 독립적으로 동작)
        */
        accountNotSupportedService.saveAccountWhenHistoryNotSupported(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);
    }

    @DisplayName("not_supported 일 경우, 기본적으로 트랜잭션이 없이 동작하기 때문에 예외가 발생해도 AccountHistroy 가 저장된다")
    @Test
    void saveAccountWhenHistoryThrowException() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        accountNotSupportedService.saveAccountWhenHistoryThrowException(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);
    }

    @DisplayName("not_supported일 경우, 부모 메서드가 트랜잭션이 존재하지 않을 경우, 자식 메서드는 기본적으로 트랜잭션없이 동작한다")
    @Test
    void saveAccountWithOutTransaction() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        accountNotSupportedService.saveAccountWithOutTransaction(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);

    }
}
