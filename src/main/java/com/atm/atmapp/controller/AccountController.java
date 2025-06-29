package com.atm.atmapp.controller;

import com.atm.atmapp.dto.AccountDto;
import com.atm.atmapp.dto.LoginDto;
import com.atm.atmapp.iaccountService.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        AccountDto created = accountService.createAccount(accountDto);
        return ResponseEntity.ok(created);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        AccountDto accountDto=accountService.login(loginDto);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        AccountDto account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(@RequestParam Long id, @RequestParam Double amount) {
        AccountDto updated = accountService.withdraw(id, amount);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestParam Long id, @RequestParam Double amount) {
        AccountDto updated = accountService.deposit(id, amount);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<Double> getBalance(@PathVariable Long id) {
        Double balance = accountService.getBalance(id);
        return ResponseEntity.ok(balance);
    }

}
