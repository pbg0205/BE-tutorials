package com.cooper.springcoreevent.account.exception;

public class InvalidMoneyException extends RuntimeException {

    public InvalidMoneyException(int currentBalance, int withdrawalMoney) {
        super(String.format("유효하지 않은 출금액 입니다. (현재 잔고 : %d ,인출 금액 : %d)", currentBalance, withdrawalMoney));
    }

}
