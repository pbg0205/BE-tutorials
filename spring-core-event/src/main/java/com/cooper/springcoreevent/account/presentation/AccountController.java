package com.cooper.springcoreevent.account.presentation;

import com.cooper.springcoreevent.account.business.AccountService;
import com.cooper.springcoreevent.account.domain.Account;
import com.cooper.springcoreevent.account.dto.MoneyRequestDto;
import com.cooper.springcoreevent.account.dto.MoneyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/withdrawal")
    public ResponseEntity<MoneyResponseDto> withdraw(@RequestBody MoneyRequestDto moneyRequestDto) {
        MoneyResponseDto moneyResponseDto = accountService.withdraw(moneyRequestDto);
        return ResponseEntity.ok(moneyResponseDto);
    }

    @PutMapping("/deposit")
    public ResponseEntity<MoneyResponseDto> deposit(@RequestBody MoneyRequestDto moneyRequestDto) {
        MoneyResponseDto moneyResponseDto = accountService.deposit(moneyRequestDto);
        return ResponseEntity.ok(moneyResponseDto);
    }

}
