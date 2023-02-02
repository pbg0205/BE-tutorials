package com.cooper.springcoreevent.account.business;

import com.cooper.springcoreevent.account.domain.Account;
import com.cooper.springcoreevent.account.domain.AccountRepository;
import com.cooper.springcoreevent.account.dto.MoneyRequestDto;
import com.cooper.springcoreevent.account.dto.MoneyResponseDto;
import com.cooper.springcoreevent.account.event.AccountResultEvent;
import com.cooper.springcoreevent.account.exception.InvalidMoneyException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final AccountRepository accountRepository;

    @Transactional
    public MoneyResponseDto deposit(MoneyRequestDto moneyRequestDto) {
        Account account = accountRepository.findById(moneyRequestDto.getId())
                .orElseThrow(RuntimeException::new);
        account.deposit(moneyRequestDto.getMoney());
        applicationEventPublisher.publishEvent(new AccountResultEvent(account.getName(), account.getMoney()));
        return MoneyResponseDto.of(account.getId(), account.getName(), account.getMoney());
    }

    @Transactional
    public MoneyResponseDto withdraw(MoneyRequestDto moneyRequestDto) {
        Account account = accountRepository.findById(moneyRequestDto.getId())
                .orElseThrow(RuntimeException::new);
        try {
            account.withdraw(moneyRequestDto.getMoney());
        } catch (InvalidMoneyException invalidMoneyException) {
            // 이벤트를 먼저 전송하고 커밋, 롤백 여부에 알맞는 트랜잭션 리스너에 이벤트를 전달한다.
            applicationEventPublisher.publishEvent(new AccountResultEvent(account.getName(), account.getMoney()));
            throw new RuntimeException("출금에 실패했습니다.", invalidMoneyException);
        }

        return MoneyResponseDto.of(account.getId(), account.getName(), account.getMoney());
    }
}
