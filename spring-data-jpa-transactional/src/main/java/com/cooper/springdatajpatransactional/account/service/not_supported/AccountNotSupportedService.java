package com.cooper.springdatajpatransactional.account.service.not_supported;

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
public class AccountNotSupportedService {

    private final AccountHistoryNotSupportedService accountHistoryNotSupportedService;

    private final AccountRepository accountRepository;

    @Transactional
    public void saveAccountWhenHistoryNotSupported(Account account) {
        Account savedAccount = accountRepository.save(account);

        AccountHistoryCreateRequestDto accountHistoryCreateRequestDto = AccountHistoryCreateRequestDto.create(
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        accountHistoryNotSupportedService.save(accountHistoryCreateRequestDto);
    }

    @Transactional
    public void saveAccountWhenHistoryThrowException(Account account) {
        Account savedAccount = accountRepository.save(account);

        AccountHistoryCreateRequestDto accountHistoryCreateRequestDto = AccountHistoryCreateRequestDto.create(
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        try {
            accountHistoryNotSupportedService.throwExceptionWhenNotSupported(accountHistoryCreateRequestDto);
        } catch (Exception exception) {
            log.debug("exception : {}", exception.getMessage());
        }
    }

    public void saveAccountWithOutTransaction(Account account) {
        Account savedAccount = accountRepository.save(account);

        AccountHistoryCreateRequestDto accountHistoryCreateRequestDto = AccountHistoryCreateRequestDto.create(
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        try {
            accountHistoryNotSupportedService.throwExceptionWhenNotSupported(accountHistoryCreateRequestDto);
        } catch (Exception exception) {
            log.debug("exception : {}", exception.getMessage());
        }
    }

}
