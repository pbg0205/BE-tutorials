package com.cooper.springdatajpatransactional.account.service;

import com.cooper.springdatajpatransactional.account.domain.Account;
import com.cooper.springdatajpatransactional.account.domain.AccountHistory;
import com.cooper.springdatajpatransactional.account.repository.AccountHistoryRepository;
import com.cooper.springdatajpatransactional.account.repository.AccountRepository;
import com.cooper.springdatajpatransactional.account.service.never.AccountNeverService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AccountNeverServiceTest {

    @Autowired
    private AccountNeverService accountNeverService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @DisplayName("never 일 경우, 부모 트랜잭션이 존재하면 예외를 반환한다.")
    @Test
    void saveAccountWhenHistoryNever() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        assertThatThrownBy(() -> accountNeverService.saveAccountWithTransaction(Account.create("345", "345", 345)))
                .isInstanceOf(IllegalTransactionStateException.class);

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        //IllegalTransactionStateException 이 발생해 Account, AccountHistory 둘다 생성 X
        assertThat(allAccounts).hasSize(2);
        assertThat(allAccountHistories).hasSize(0);
    }

    @DisplayName("never 일 경우, 부모 트랜잭션이 존재하지 않으면 트랜잭션없이 동작한다")
    @Test
    void saveAccountWithoutTransaction() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        accountNeverService.saveAccountWithOutTransaction(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);
    }

    @DisplayName("never 일 경우, 트랜잭션이 묶이지 않기 때문에 예외를 발생시켜도 롤백하지 않는다")
    @Test
    void saveAccountWhenChildTransactionPropagationNever() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        assertThatThrownBy(() -> accountNeverService.saveAccountWhenHistoryThrowExceptionWithoutTransaction(Account.create("345", "345", 345)))
                .isInstanceOf(RuntimeException.class);

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);
    }

}
