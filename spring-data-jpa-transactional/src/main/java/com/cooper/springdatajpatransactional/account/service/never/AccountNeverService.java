package com.cooper.springdatajpatransactional.account.service.never;

import com.cooper.springdatajpatransactional.account.domain.Account;
import com.cooper.springdatajpatransactional.account.dto.AccountHistoryCreateRequestDto;
import com.cooper.springdatajpatransactional.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountNeverService {

    private final AccountHistoryNeverService accountHistorySupportsService;

    private final AccountRepository accountRepository;

    @Transactional
    public void saveAccountWithTransaction(Account account) {
        Account savedAccount = accountRepository.save(account);

        AccountHistoryCreateRequestDto accountHistoryCreateRequestDto = AccountHistoryCreateRequestDto.create(
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        accountHistorySupportsService.save(accountHistoryCreateRequestDto);
    }

    public void saveAccountWithOutTransaction(Account account) {
        Account savedAccount = accountRepository.save(account);

        AccountHistoryCreateRequestDto accountHistoryCreateRequestDto = AccountHistoryCreateRequestDto.create(
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        accountHistorySupportsService.save(accountHistoryCreateRequestDto);
    }

    public void saveAccountWhenHistoryThrowExceptionWithoutTransaction(Account account) {
        Account savedAccount = accountRepository.save(account);

        AccountHistoryCreateRequestDto accountHistoryCreateRequestDto = AccountHistoryCreateRequestDto.create(
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        accountHistorySupportsService.throwExceptionWhenSupports(accountHistoryCreateRequestDto);
    }

}
