package com.cooper.springcoreevent.account.listener;

import com.cooper.springcoreevent.account.event.AccountResultEvent;
import com.cooper.springcoreevent.account.event.AccountTrialEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class AccountTransactionListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void logBeforeCommit(AccountTrialEvent accountTrialEvent) {
        log.info("BeforeCommit : 계좌 금액 변경 시도 - {}", accountTrialEvent);
    }

    @Order(1)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void logAfterWithdraw(AccountResultEvent accountResultEvent) {
        log.info("AfterCommit : 계좌 금액 변경 성공 - {}", accountResultEvent);
    }

    @Order(2)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void logAfterCompletion(AccountResultEvent accountResultEvent) {
        log.info("AfterCompletion : 계좌 금액 변경 요청 완료");
    }

    @Order(1)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void logAfterRollback(AccountResultEvent accountResultEvent) {
        log.info("AfterRollback : 계좌 금액 변경 실패 - {}", accountResultEvent);
    }

}
