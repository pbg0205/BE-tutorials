package com.cooper.springdatajpatransactional.account.service.never;

import com.cooper.springdatajpatransactional.account.domain.AccountHistory;
import com.cooper.springdatajpatransactional.account.dto.AccountHistoryCreateRequestDto;
import com.cooper.springdatajpatransactional.account.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountHistoryNeverService {

    private final AccountHistoryRepository accountHistoryRepository;

    @Transactional(propagation = Propagation.NEVER)
    public void save(AccountHistoryCreateRequestDto accountHistoryCreateRequestDto) {
        AccountHistory accountHistory = accountHistoryCreateRequestDto.fromEntity();
        accountHistoryRepository.save(accountHistory);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void throwExceptionWhenSupports(AccountHistoryCreateRequestDto accountHistoryCreateRequestDto) {
            AccountHistory accountHistory = accountHistoryCreateRequestDto.fromEntity();
            accountHistoryRepository.save(accountHistory);
            throw new RuntimeException();
    }

}