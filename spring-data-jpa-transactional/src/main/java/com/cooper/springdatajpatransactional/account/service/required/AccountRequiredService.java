package com.cooper.springdatajpatransactional.account.service.required;

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
public class AccountRequiredService {

    private final AccountRepository accountRepository;
    private final AccountHistoryRequiredService accountHistoryRequiredService;

    @Transactional
    public void saveAccountWhenHistoryRequired(Account account) {
        Account savedAccount = accountRepository.save(account);

        AccountHistoryCreateRequestDto accountHistoryCreateRequestDto = AccountHistoryCreateRequestDto.create(
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        accountHistoryRequiredService.save(accountHistoryCreateRequestDto);
    }

    @Transactional
    public void saveAccountWhenHistoryThrowException(Account account) {
        Account savedAccount = accountRepository.save(account);

        AccountHistoryCreateRequestDto accountHistoryCreateRequestDto = AccountHistoryCreateRequestDto.create(
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        try {
            accountHistoryRequiredService.throwExceptionWhenRequired(accountHistoryCreateRequestDto);
        } catch (RuntimeException runtimeException) {
            log.debug("RuntimeException!!!");
        }
    }
}
