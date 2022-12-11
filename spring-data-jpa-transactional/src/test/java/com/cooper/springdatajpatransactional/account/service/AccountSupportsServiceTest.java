package com.cooper.springdatajpatransactional.account.service;

import com.cooper.springdatajpatransactional.account.domain.Account;
import com.cooper.springdatajpatransactional.account.domain.AccountHistory;
import com.cooper.springdatajpatransactional.account.repository.AccountHistoryRepository;
import com.cooper.springdatajpatransactional.account.repository.AccountRepository;
import com.cooper.springdatajpatransactional.account.service.supports.AccountSupportsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountSupportsServiceTest {

    @Autowired
    private AccountSupportsService accountSupportsService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @BeforeEach
    void init() {
        accountRepository.deleteAll();
        accountHistoryRepository.deleteAll();
    }

    @DisplayName("supports 일 경우, 부모 트랜잭션이 존재하면 부모 트랜잭션 내에서 동작한다")
    @Test
    void saveAccountWhenHistorySupports() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        // if transaction commit,  Account 1 + AccountHistory 1
        accountSupportsService.saveAccountWhenHistorySupports(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);
    }

    @DisplayName("supports 일 경우, 자식 트랜잭션에서 예외가 발생하면 부모 트랜잭션까지 롤백한다.")
    @Test
    void throwExceptionWhenSupports() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        //if transaction commit,  Account 1 + AccountHistory 1
        Assertions.assertThatThrownBy(() -> accountSupportsService.saveAccountWhenHistoryThrowException(Account.create("345", "345", 345)))
                .isInstanceOf(UnexpectedRollbackException.class);

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(2);
        assertThat(allAccountHistories).hasSize(0);
    }

    @DisplayName("supports 일 경우, 부모 트랜잭션이 존재하지 않으면 트랜잭션없이 동작한다.")
    @Test
    void saveAccountWithOutTransaction() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        accountSupportsService.saveAccountWithOutTransaction(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        //자식 트랜잭션이 동작하지 않기 때문에 AccountHistory 가 저장된다.
        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);
    }

}
