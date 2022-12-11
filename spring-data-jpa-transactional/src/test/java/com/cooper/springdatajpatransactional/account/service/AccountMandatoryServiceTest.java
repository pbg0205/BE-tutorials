package com.cooper.springdatajpatransactional.account.service;

import com.cooper.springdatajpatransactional.account.domain.Account;
import com.cooper.springdatajpatransactional.account.domain.AccountHistory;
import com.cooper.springdatajpatransactional.account.repository.AccountHistoryRepository;
import com.cooper.springdatajpatransactional.account.repository.AccountRepository;
import com.cooper.springdatajpatransactional.account.service.mandatory.AccountMandatoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountMandatoryServiceTest {

    @Autowired
    private AccountMandatoryService accountMandatoryService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @DisplayName("supports 일 경우, 하나의 트랜잭션으로 묶인다")
    @Test
    void saveAccountWhenHistorySupports() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        accountMandatoryService.saveAccountWithTransaction(Account.create("345", "345", 345));

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(1);
    }

    @DisplayName("supports 일 경우, 자식 트랜잭션에서 예외가 발생하면 전체 롤백한다")
    @Test
    void throwExceptionWhenSupports() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        Assertions.assertThatThrownBy(() -> accountMandatoryService.saveAccountWhenHistoryThrowException(Account.create("345", "345", 345)))
                .isInstanceOf(UnexpectedRollbackException.class);

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(2);
        assertThat(allAccountHistories).hasSize(0);
    }

    @DisplayName("supports 일 경우, 부모 트랜잭션이 존재하지 않으면 예외를 발생한다")
    @Test
    void saveAccountWithoutParentTransaction() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        Assertions.assertThatThrownBy(() -> accountMandatoryService.saveAccountWithOutTransaction(Account.create("345", "345", 345)))
                .isInstanceOf(IllegalTransactionStateException.class);

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(0);
    }

    @DisplayName("supports 일 경우, 부모 트랜잭션이 존재하지 않으면 예외를 발생하고 이후 동작을 확인한다")
    @Test
    void saveAccountWhenHistoryThrowExceptionWithoutTransaction() {
        accountRepository.save(Account.create("123", "123", 123));
        accountRepository.save(Account.create("234", "234", 234));

        //IllegalTransactionStateException 은 RuntimeException 하위 예외
        Assertions.assertThatThrownBy(() -> accountMandatoryService.saveAccountWhenHistoryThrowExceptionWithoutTransaction(Account.create("345", "345", 345)))
                .isInstanceOf(IllegalTransactionStateException.class);

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountHistory> allAccountHistories = accountHistoryRepository.findAll();

        assertThat(allAccounts).hasSize(3);
        assertThat(allAccountHistories).hasSize(0);
    }


}
